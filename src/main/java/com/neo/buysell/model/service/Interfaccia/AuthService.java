package com.neo.buysell.model.service.Interfaccia;

import com.neo.buysell.model.dto.RegisterRequest;
import com.neo.buysell.model.enumeration.Role;

public interface AuthService {
    boolean login(String username, String password);
    boolean register(RegisterRequest registerRequest, Role role);
}
