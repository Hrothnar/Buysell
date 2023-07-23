package com.neo.buysell.model.service.Interfaccia;

import com.neo.buysell.model.dto.other.Credentials;

public interface AuthService {
    void login(Credentials credentials);
    void register(Credentials credentials);
}
