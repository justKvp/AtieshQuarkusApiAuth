package io.iqark.tcauth.service.impl;

import io.iqark.tcauth.entity.Account;
import io.iqark.tcauth.entity.RealmCharacter;
import io.iqark.tcauth.entity.Realmlist;
import io.iqark.tcauth.pojo.AccountCreateRq;
import io.iqark.tcauth.pojo.AccountVerifyRq;
import io.iqark.tcauth.pojo.CustomResponse;
import io.iqark.tcauth.service.AccessService;
import io.iqark.tcauth.service.AuthService;
import io.iqark.tcauth.utils.RUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static io.iqark.tcauth.utils.SRP6.*;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    AccessService accessService;

    @Override
    public Response getAccount(String userName) {
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.preconditionFailed(1, String.format("Account %s does not exist", userName));
        }
        return RUtil.success(account);
    }

    @Override
    public Response getAccountAccess(String userName, String authorization) {
        try {
            if (!accessService.isLegalUser(authorization)) {
                return RUtil.authorizedFailed(1, "Wrong authorization");
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            return RUtil.expectationFailed(2, String.format("getAccountAccess failed : %s", e.getMessage()));
        }
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.preconditionFailed(1, String.format("Account %s does not exist", userName));
        }
        return RUtil.success(account.accountAccess);
    }

    @Override
    public Response verifyAccount(AccountVerifyRq accountVerifyRq) {
        Account account = Account.findByUsername(accountVerifyRq.getAccount_name().toUpperCase());
        if (account == null) {
            return RUtil.preconditionFailed(1, String.format("Account %s does not exist", accountVerifyRq.getAccount_name()));
        }

        try {
            return verifySRP6(accountVerifyRq.getAccount_name(),
                    accountVerifyRq.getAccount_password(),
                    account.getSalt(),
                    account.getVerifier()) ? RUtil.success(new CustomResponse()) : RUtil.preconditionFailed(1, "Login or Password incorrect");
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response createAccount(AccountCreateRq accountCreateRq) {
        Account account = Account.findByUsername(accountCreateRq.getAccount_name().toUpperCase());
        if (account != null) {
            return RUtil.preconditionFailed(1, String.format("Account %s already exist", accountCreateRq.getAccount_name()));
        }
        try {
            addAccount(accountCreateRq);
        } catch (NoSuchAlgorithmException | IOException e) {
            return RUtil.expectationFailed(2, String.format("createAccount failed : %s", e.getMessage()));
        }
        return RUtil.success(new CustomResponse());
    }

    @Override
    public Response getRealmlists() {
        List<Realmlist> realms = Realmlist.listAll();
        return RUtil.success(realms);
    }

    @Override
    public Response generateToken() {
        String token =
                Jwt.issuer("jwt-token")
                        .subject("account")
                        .groups("user")
                        .expiresAt(System.currentTimeMillis() + 3600)
                        .sign();
        return RUtil.success(new CustomResponse(token));
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected void addAccount(AccountCreateRq accountCreateRq) throws IOException, NoSuchAlgorithmException {
        byte[] salt = generateRandomSalt();
        byte[] verifier = calculateSRP6Verifier(accountCreateRq.getAccount_name(),
                accountCreateRq.getAccount_password(),
                salt);

        Account account = getAccount(accountCreateRq, salt, verifier);

        List<Realmlist> realmlists = Realmlist.listAll();
        for (Realmlist it : realmlists) {
            RealmCharacter realmCharacter = new RealmCharacter();
            realmCharacter.setRealmid(it.getId());
            realmCharacter.setNumchars(0);
            realmCharacter.account = account;
            realmCharacter.persist();
        }
    }

    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    protected static Account getAccount(AccountCreateRq accountCreateRq, byte[] salt, byte[] verifier) {
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
        return account;
    }
}
