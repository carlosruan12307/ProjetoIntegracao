package com.auth.auth.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth.auth.DTOs.UserGoogleModel;
import com.auth.auth.responses.GoogleResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class GoogleIdTokenVerifier {

    public UserGoogleModel verifierAndReturnDataUser(GoogleResponse response)
            throws GeneralSecurityException, IOException {
        com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier verifier = new com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections
                        .singletonList(response.getClient_id()))
                // Or, if multiple clients access the backend:
                // .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(response.getCredential());
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            UserGoogleModel user = new UserGoogleModel((String) payload.getSubject(), payload.getEmail(),
                    payload.getEmailVerified(), (String) payload.get("name"), (String) payload.get("picture"));
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
