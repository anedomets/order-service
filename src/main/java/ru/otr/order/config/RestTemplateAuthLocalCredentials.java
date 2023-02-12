package ru.otr.order.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otr.order.property.LoginProperty;

import java.util.List;


@EnableConfigurationProperties(LoginProperty.class)
@Component("restTemplateLocalCredentials")
@Slf4j
public class RestTemplateAuthLocalCredentials extends RestTemplate {
    private LoginProperty loginProperty;

    @Autowired
    public RestTemplateAuthLocalCredentials(LoginProperty loginProperty) {
        this.loginProperty = loginProperty;
        List<ClientHttpRequestInterceptor> interceptors = this.getInterceptors();
        ClientHttpRequestInterceptor clientHttpRequestInterceptor = (request, body, execution) -> {
           if(checkLocalCredentials()) {
               request.getHeaders().setBearerAuth(getToken());
            }
            return execution.execute(request, body);
        };

        interceptors.add(clientHttpRequestInterceptor);
        this.setInterceptors(interceptors);
    }

    /**
     * Проверка настроек
     * @return
     */
    private Boolean checkLocalCredentials(){
        return checkCredential(loginProperty.getUrl().getBase()) && checkCredential(loginProperty.getUrl().getLogin()) && checkCredential(loginProperty.getAuth().getUser()) && checkCredential(loginProperty.getAuth().getPassword()) && checkCredential(loginProperty.getAuth().getClientId());
    }

    /**
     * Проверка поля
     * @param credential
     * @return
     */
    private Boolean checkCredential(String credential){
        return !(credential == null || credential.isEmpty());
    }

    /**
     * Получение токена доступа
     * @return
     */
    private String getToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<AuthResponse> objectResponseEntity = restTemplate.postForEntity(
                loginProperty.getUrl().getBase() + loginProperty.getUrl().getLogin(),
                new HttpEntity<>(loginProperty.getAuth().getCredentials(), headers), AuthResponse.class);

        return objectResponseEntity.getBody().getAccessToken();
    }

    @Data
    @NoArgsConstructor
    private class AuthResponse {

        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("refresh_expires_in")
        private Integer refreshExpiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("not-before-policy")
        private Integer notBeforePolicy;
        @JsonProperty("session_state")
        private String sessionState;
        @JsonProperty("scope")
        private String scope;

    }


}
