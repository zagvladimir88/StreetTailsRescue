package com.zagvladimir.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;

import com.zagvladimir.model.User;
import com.zagvladimir.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    private final UserService userRepository;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserService userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findUserByLogin(userDetails.getUsername()).get());
    }

    public void logout() {
        authenticationContext.logout();
    }

}
