package com.bunary.vocab.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunary.vocab.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @EntityGraph(attributePaths = { "permissions" })
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByRoleWithPermissions(String name);
}
