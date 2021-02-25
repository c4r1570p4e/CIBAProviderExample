package com.cl.poc.ciba.keycloak.protocol;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.ciba.decoupledauthn.AuthenticationChannelProviderFactory;

public class MyDelegateDecoupledAuthenticationProviderFactory implements AuthenticationChannelProviderFactory {

    public static final String PROVIDER_ID = "my-decoupled-http-authn-channel";

    private String decoupledAuthenticationRequestUri;

    @Override
    public MyHttpAuthenticationChannelProvider create(KeycloakSession session) {
        return new MyHttpAuthenticationChannelProvider(session, decoupledAuthenticationRequestUri);
    }

    @Override
    public void init(Scope config) {
        decoupledAuthenticationRequestUri = config.get("decoupledAuthnRequestUri");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
