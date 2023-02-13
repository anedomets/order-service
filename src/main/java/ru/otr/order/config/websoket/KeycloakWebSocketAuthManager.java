package ru.otr.order.config.websoket;

import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("websoket")
@AllArgsConstructor
public class KeycloakWebSocketAuthManager implements AuthenticationManager
{

    private final KeycloakTokenVerifier tokenVerifier;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        JWSAuthenticationToken auth = (JWSAuthenticationToken) authentication;
        String tokenString = (String) auth.getCredentials();
        try
        {
            AccessToken accessToken = tokenVerifier.verifyToken(tokenString);
            List<GrantedAuthority> collect = accessToken.getRealmAccess().getRoles()
                    .stream()
                    .map(role -> new KeycloakRole(role))
                    .collect(Collectors.toList());
            /*auth = new JWSAuthenticationToken(bearerToken,collect,accessToken.getSubject());
            auth.setAuthenticated(true);*/
        }
        catch (VerificationException e)
        {
            e.printStackTrace();
        }
        return auth;
    }

}
