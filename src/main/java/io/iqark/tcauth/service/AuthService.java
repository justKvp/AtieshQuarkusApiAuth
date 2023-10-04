package io.iqark.tcauth.service;

import io.iqark.tcauth.pojo.AccountCreateRq;
import io.iqark.tcauth.pojo.AccountVerifyRq;

import jakarta.ws.rs.core.Response;

public interface AuthService {
    Response getAccount(String userName);
    Response getAccountAccess(String userName, String authorization);
    Response verifyAccount(AccountVerifyRq accountCreateRq);
    Response createAccount(AccountCreateRq accountCreateRq);
    Response getRealmlists();
    Response generateToken();
}
