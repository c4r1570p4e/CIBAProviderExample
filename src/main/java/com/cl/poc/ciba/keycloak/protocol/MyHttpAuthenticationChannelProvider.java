package com.cl.poc.ciba.keycloak.protocol;

import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.common.util.Time;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.protocol.ciba.decoupledauthn.HttpAuthenticationChannelProvider;
import org.keycloak.protocol.ciba.endpoints.request.BackchannelAuthenticationRequest;

public class MyHttpAuthenticationChannelProvider extends HttpAuthenticationChannelProvider {

    public MyHttpAuthenticationChannelProvider(KeycloakSession session, String decoupledAuthenticationRequestUri) {
        super(session, decoupledAuthenticationRequestUri);
    }

    @Override
    protected SimpleHttp completeDecoupledAuthnRequest(SimpleHttp simpleHttp, UserModel user, ClientModel client, BackchannelAuthenticationRequest request, int expiresIn, String authResultId) {
        return super.completeDecoupledAuthnRequest(simpleHttp, user, client, request, expiresIn, authResultId)
                /* I may need more attributes of my user in decoupled server process to do the authentication */
                .param("USER_MAIL", user.getEmail())
                .param("USER_MOBILE", user.getFirstAttribute("mobile"))
                /* as "requested_expiry" might be provided by the Client during the CIBA Authentication Request
                * and for more friendly user experience Authentication Device may terminate the process when the Client is
                * no longer interested in the result of authentication (ex : stop to try notify the Smartphone Authentication
                * Application, or if using an interactive voice server stop ringing the user phone.
                */
                .param("END_OF_NOTIFICATION", Long.toString(Time.currentTime() + expiresIn))
                /* I may need some attributes about the client in decoupled server process
                It might be the Authentication device to use, a mobile phone application, an interactive voice server...
                And my decoupled server has responsibility to route to the good device.
                */
                .param("AUTHENTICATION_DEVICE", client.getAttribute("channel"));
    }

}
