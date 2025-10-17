package com.bunary.vocab.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.RoleResDTO;
import com.bunary.vocab.model.Role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleMapper {

    public RoleResDTO converToRoleResDTO(Role role) {
        RoleResDTO roleResDTO = new RoleResDTO();
        roleResDTO.setId(role.getId());
        roleResDTO.setName(role.getName());

        return roleResDTO;
    }

    public Set<RoleResDTO> convertToRoleResDTO(Set<Role> role) {
        Set<RoleResDTO> roleResDTO = role.stream().map((r) -> {
            return this.converToRoleResDTO(r);
        }).collect(Collectors.toSet());

        return roleResDTO;
    }

}
