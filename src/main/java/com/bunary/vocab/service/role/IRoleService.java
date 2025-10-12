package com.bunary.vocab.service.role;

import com.bunary.vocab.dto.reponse.RoleResDTO;
import com.bunary.vocab.model.Role;

public interface IRoleService {
    Role findByName(String name);

}
