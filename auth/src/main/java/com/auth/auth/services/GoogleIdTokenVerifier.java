package com.auth.auth.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth.auth.DTOs.JwtClaimsModel;

import com.auth.auth.enums.RoleEnum;
import com.auth.auth.models.RoleModel;
import com.auth.auth.models.UserModel;
import com.auth.auth.responses.GoogleResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class GoogleIdTokenVerifier {
    @Value("${google.clientId}")
    private String clientId;

    public UserModel verifierAndReturnDataUser(String credentials)
            throws GeneralSecurityException, IOException {
        com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier verifier = new com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections
                        .singletonList(clientId))
                // Or, if multiple clients access the backend:
                // .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(credentials);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            List<RoleModel> roleModels = new ArrayList<>();
            RoleModel roleModel = new RoleModel(RoleEnum.ROLE_USER);
            roleModels.add(roleModel);

            UserModel user = new UserModel(payload.getEmail(), roleModels, (String) payload.get("picture"));

            // UserGoogleModel user = new UserGoogleModel((String) payload.getSubject(),
            // payload.getEmail(),
            // payload.getEmailVerified(), (String) payload.get("name"), (String)
            // payload.get("picture"));
            // boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            // String name = (String) payload.get("name");
            // String pictureUrl = (String) payload.get("picture");
            // String locale = (String) payload.get("locale");
            // String familyName = (String) payload.get("family_name");
            // String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            return user;
        } else {

            throw new RuntimeException("invalid ID token");
        }
    }
}
