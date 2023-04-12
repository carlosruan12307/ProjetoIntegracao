package com.auth.auth.repositorys;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel,UUID>  {
     Optional<UserModel> findByemail(String email);
}
