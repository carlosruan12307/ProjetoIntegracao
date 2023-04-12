package com.auth.auth.repositorys;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.enums.RoleEnum;
import com.auth.auth.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,UUID> {
    Optional<RoleModel> findByname(RoleEnum roleEnum);
}
