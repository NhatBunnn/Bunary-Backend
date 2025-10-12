package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.RoleResDTO;
import com.bunary.vocab.model.Role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleMapper {

    public RoleResDTO converToRole(Role role) {
        RoleResDTO roleResDTO = new RoleResDTO();
        roleResDTO.setId(role.getId());
        roleResDTO.setName(role.getName());

        return roleResDTO;
    }

}
