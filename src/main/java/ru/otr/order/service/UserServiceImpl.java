package ru.otr.order.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.otr.order.property.UserServiceProperty;

@EnableConfigurationProperties(UserServiceProperty.class)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserServiceProperty userServiceProperty;



}
