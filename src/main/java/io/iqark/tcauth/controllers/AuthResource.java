package io.iqark.tcauth.controllers;

import io.iqark.tcauth.pojo.AccountCreateRq;
import io.iqark.tcauth.pojo.AccountVerifyRq;
import io.iqark.tcauth.service.AuthService;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@Path("/auth")
public class AuthResource {
    @Inject
    AuthService authService;

    @GET
    @RolesAllowed("user")
    @Path("/getAccount/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam(value = "userName") String userName) {
        return authService.getAccount(userName);
    }

    @GET
    @RolesAllowed("user")
    @Path("/getAccountAccess/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    @SecurityRequirement(name = "Basic Authentication")
    @SecurityScheme(securitySchemeName = "Basic Authentication", scheme = "basic", type = SecuritySchemeType.HTTP, description = "Enter login and password", in = SecuritySchemeIn.HEADER)
    public Response getAccountAccess(@PathParam(value = "userName") String userName,
                                     @HeaderParam("AuthToken") String authorization) {
        return authService.getAccountAccess(userName, authorization);
    }

    @POST
    @PermitAll
    @Path("/verifyAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyAccount(@Valid AccountVerifyRq accountVerifyRq) {
        return authService.verifyAccount(accountVerifyRq);
    }

    @POST
    @RolesAllowed("user")
    @Path("/createAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@Valid AccountCreateRq accountCreateRq) {
        return authService.createAccount(accountCreateRq);
    }

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken() {
        return authService.generateToken();
    }

    @GET
    @Path("/realmlist")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRealmlists() {
        return authService.getRealmlists();
    }
}