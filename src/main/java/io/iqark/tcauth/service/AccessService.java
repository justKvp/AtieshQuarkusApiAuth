package io.iqark.tcauth.service;

import io.iqark.tcauth.entity.AccountAccess;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static io.iqark.tcauth.utils.Base64Utils.base64Decode;
import static io.iqark.tcauth.utils.SRP6.verifySRP6;

@ApplicationScoped
public class AccessService {
    private List<AccountAccess> accountAccesses = null;

    public void fillAccountAccessList() {
        accountAccesses = AccountAccess.listAll();
    }

    public boolean isLegalUser(String base64LoginPass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (base64LoginPass == null) {
            return false;
        }
        String[] part = base64Decode(base64LoginPass.replace("Basic ", "")).split(":");
        if (part.length < 2) {
            return false;
        }
        String login = part[0];
        AccountAccess accountAccess = getAccountByAccess(login);
        if (accountAccess == null) {
            return false;
        }
        String password = part[1];
        return verifySRP6(accountAccess.account.getUsername(),
                password,
                accountAccess.account.getSalt(),
                accountAccess.account.getVerifier());
    }

    public boolean isContainAccountID(Integer id) {
        return accountAccesses.stream()
                .anyMatch(accountAccess -> accountAccess.getAccountID().equals(id));
    }

    public boolean isContainAccountName(String name) {
        return accountAccesses.stream()
                .anyMatch(accountAccess -> accountAccess.account.getUsername().equals(name.toUpperCase()));
    }

    public AccountAccess getAccountByAccess(String name) {
        return accountAccesses.stream()
                .filter(accountAccess -> accountAccess.account.getUsername().equals(name.toUpperCase()))
                .findFirst().orElse(null);
    }

}
