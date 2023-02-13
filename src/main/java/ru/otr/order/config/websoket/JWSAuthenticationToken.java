package ru.otr.order.config.websoket;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JWSAuthenticationToken extends AbstractAuthenticationToken implements Authentication
{

    private static final long serialVersionUID = 1L;

    private String token;
    private String principal;

    public JWSAuthenticationToken(String token,Collection<GrantedAuthority> authorities,String principal)
    {
        super(authorities);
        this.token = token;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal()
    {
        return this.principal;
    }

}

