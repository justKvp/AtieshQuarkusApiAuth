package io.iqark.tcauth.service.impl;

import io.iqark.tcauth.entity.Account;
import io.iqark.tcauth.entity.AccountAccess;
import io.iqark.tcauth.entity.RealmCharacter;
import io.iqark.tcauth.entity.Realmlist;
import io.iqark.tcauth.pojo.AccountCreateRq;
import io.iqark.tcauth.pojo.AccountVerifyRq;
import io.iqark.tcauth.pojo.CustomResponse;
import io.iqark.tcauth.service.AccessService;
import io.iqark.tcauth.service.AuthService;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static io.iqark.tcauth.utils.Utils.*;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    AccessService accessService;

    @Override
    public Response getAccount(String userName) {
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return Response.status(Response.Status.OK)
                    .entity(new CustomResponse("getAccount", String.format("Account %s does not exist", userName)))
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(account)
                .build();
    }

    @Override
    public Response getAccountAccess(String userName, String authorization) {
        if (!accessService.isLegalUser(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return Response.status(Response.Status.OK)
                    .entity(new CustomResponse("getAccount", String.format("Account %s does not exist", userName)))
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(account.accountAccess)
                .build();
    }

    @Override
    public Response verifyAccount(AccountVerifyRq accountVerifyRq) {
        Account account = Account.findByUsername(accountVerifyRq.getAccount_name().toUpperCase());
        if (account == null) {
            return Response.status(Response.Status.OK)
                    .entity(new CustomResponse("verifyAccount", String.format("Account %s does not exist", accountVerifyRq.getAccount_name())))
                    .build();
        }

        if (!verifySRP6(accountVerifyRq.getAccount_name(), accountVerifyRq.getAccount_password(), account.getSalt(), account.getVerifier())) {
            return Response.status(Response.Status.OK)
                    .entity(new CustomResponse("verifyAccount", "Login or Password incorrect"))
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(new CustomResponse())
                .build();
    }

    @Override
    public Response createAccount(AccountCreateRq accountCreateRq) {
        Account account = Account.findByUsername(accountCreateRq.getAccount_name().toUpperCase());
        if (account != null) {
            return Response.status(Response.Status.OK)
                    .entity(new CustomResponse("createAccount", String.format("Account %s already exist", accountCreateRq.getAccount_name())))
                    .build();
        }

        addAccount(accountCreateRq);
        return Response.status(Response.Status.OK)
                .entity(new CustomResponse())
                .build();
    }

    @Override
    public Response getRealmlists() {
        List<Realmlist> realms = Realmlist.listAll();
        return Response.status(Response.Status.OK)
                .entity(realms)
                .build();
    }

    @Override
    public Response generateToken() {
        String token =
                Jwt.issuer("jwt-token")
                        .subject("account")
                        .groups("user")
                        .expiresAt(System.currentTimeMillis() + 3600)
                        .sign();
        return Response.status(Response.Status.OK)
                .entity(new CustomResponse(token))
                .build();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected void addAccount(AccountCreateRq accountCreateRq) {
        // Reg
        byte[] salt = generateRandomSalt(32);
        byte[] verifier = calculateSRP6TCVerifier(accountCreateRq.getAccount_name(), accountCreateRq.getAccount_password(), salt);

        Account account = new Account();
        account.setUsername(accountCreateRq.getAccount_name().toUpperCase());
        account.setSalt(salt);
        account.setVerifier(verifier);
        account.setEmail(accountCreateRq.getAccount_email());
        account.setRegMail(accountCreateRq.getAccount_email());
        account.setLastIp("127.0.0.1");
        account.setLastAttemptIp("127.0.0.1");
        account.setFailedLogins(0);
        account.setLocked(0);
        account.setLockCountry("00");
        account.setOnline(0);
        account.setExpansion(2);
        account.setMuteTime(0L);
        account.setMuteReason("");
        account.setMuteBy("");
        account.setLocale(0);
        account.setOs("");
        account.setRecruiter(0);
        account.persist();

        List<Realmlist> realmlists = Realmlist.listAll();
        for (Realmlist it : realmlists) {
            RealmCharacter realmCharacter = new RealmCharacter();
            realmCharacter.setRealmid(it.getId());
            realmCharacter.setNumchars(0);
            realmCharacter.account = account;
            realmCharacter.persist();
        }
    }
}
