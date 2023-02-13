package ru.otr.order.config.websoket;

import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private KeycloakTokenVerifier tokenVerifier;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/b2b/message","/b2c/message");
        config.setApplicationDestinationPrefixes("/b2b/socket","/b2c/socket");
        config.setUserDestinationPrefix("/b2b/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/message")
                .setAllowedOrigins("http://auth-service:3001").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> tokenList = accessor.getNativeHeader("Authorization");
                    if(tokenList != null && tokenList.size() > 0){

                        String bearerToken = tokenList.get(0).replace("Bearer ", "");
                        AccessToken accessToken = null;
                        try {
                            accessToken = tokenVerifier.verifyToken(bearerToken);
                        } catch (VerificationException e) {
                            e.printStackTrace();
                        }
                        List<GrantedAuthority> collect = accessToken.getRealmAccess().getRoles().stream().map(role -> new KeycloakRole(role)).collect(Collectors.toList());
                        JWSAuthenticationToken auth = new JWSAuthenticationToken(bearerToken,collect,accessToken.getSubject());
                        auth.setAuthenticated(true);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        accessor.setUser(auth);
                    }
                }
                return message;
            }
        });
    }

}
