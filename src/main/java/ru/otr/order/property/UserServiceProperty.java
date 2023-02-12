package ru.otr.order.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(
        prefix = "user-service.url"
)
@Getter
@Setter
@Slf4j
public class UserServiceProperty {

    private String base;
    private String groups;
    private String groupsByUser;
    private String roles;
    private String rolesByUser;
    private String users;
    private String usersByGroup;
    private String usersByRole;


    @PostConstruct
    private void checkProperty(){
        if(!checkCredential(base)) {
            log.warn("Setting <user-service.url.base> not found!");
        }
        if(!checkCredential(groups)) {
            log.warn("Setting <user-service.url.groups> not found!");
        }
        if(!checkCredential(groupsByUser)) {
            log.warn("Setting <user-service.url.groupsByUser> not found!");
        }
        if(!checkCredential(roles)) {
            log.warn("Setting <user-service.url.roles> not found!");
        }
        if(!checkCredential(rolesByUser)) {
            log.warn("Setting <user-service.url.rolesByUser> not found!");
        }
        if(!checkCredential(users)) {
            log.warn("Setting <user-service.url.users> not found!");
        }
        if(!checkCredential(usersByGroup)) {
            log.warn("Setting <user-service.url.usersByGroup> not found!");
        }
        if(!checkCredential(usersByRole)) {
            log.warn("Setting <user-service.url.usersByRole> not found!");
        }
    }

    private Boolean checkCredential(String credential){
        return !(credential == null || credential.isEmpty());
    }

}
