package com.bunary.vocab.service.role;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.RoleResDTO;
import com.bunary.vocab.mapper.RoleMapper;
import com.bunary.vocab.model.Role;
import com.bunary.vocab.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }

}
