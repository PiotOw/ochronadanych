package edu.pw.ochronadanych.security;

import edu.pw.ochronadanych.entity.User;
import edu.pw.ochronadanych.security.services.LoginAttemptService;
import edu.pw.ochronadanych.security.services.UserDetailsImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @SneakyThrows
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        loginAttemptService.loginSucceeded(((UserDetailsImpl)e.getAuthentication().getPrincipal()).getUsername());
    }
}
