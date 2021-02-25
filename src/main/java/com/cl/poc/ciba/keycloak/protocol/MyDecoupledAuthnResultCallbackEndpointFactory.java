package com.cl.poc.ciba.keycloak.protocol;

import org.keycloak.models.KeycloakSession;
import org.keycloak.protocol.oidc.ext.OIDCExtProvider;
import org.keycloak.protocol.oidc.ext.OIDCExtProviderFactory;

public class MyDecoupledAuthnResultCallbackEndpointFactory implements OIDCExtProviderFactory {

    public static final String PROVIDER_ID = "my-ciba-decoupled-authn-callback";

    @Override
    public OIDCExtProvider create(KeycloakSession session) {
        return new MyHttpAuthenticationChannelProvider(session, null);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
