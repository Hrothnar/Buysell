package com.neo.buysell.model.service.contract;

import com.neo.buysell.model.dto.TRegister;
import com.neo.buysell.model.enumeration.Role;

public interface AuthServiceInt {
    boolean login(String username, String password);
    boolean register(TRegister tRegister, Role role);
}
