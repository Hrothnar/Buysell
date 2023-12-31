package com.neo.buysell.model.service;

import com.neo.buysell.model.entity.Role;
import com.neo.buysell.model.enumeration.RoleType;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRole(String name) {
        String preparedRole = RoleType.getPreparedRole(name);
        return roleRepository.findByName(preparedRole).orElseThrow(() -> new EntityNotFound(Role.class, HttpStatus.BAD_REQUEST));
    }

}
