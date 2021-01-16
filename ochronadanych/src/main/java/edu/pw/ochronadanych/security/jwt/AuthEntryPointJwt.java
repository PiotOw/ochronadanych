package edu.pw.ochronadanych.security.jwt;


import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pw.ochronadanych.security.services.LoginAttemptService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Autowired
	LoginAttemptService loginAttemptService;

	@SneakyThrows
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());


		final Map<String, Object> mapBodyException = new HashMap<>() ;

		mapBodyException.put("error"    , "Login error");
		mapBodyException.put("message"  , "Invalid credentials");
		mapBodyException.put("path"     , request.getServletPath()) ;
		mapBodyException.put("timestamp", (new Date()).getTime()) ;

		response.setContentType("application/json") ;
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

		final ObjectMapper mapper = new ObjectMapper() ;
		mapper.writeValue(response.getOutputStream(), mapBodyException) ;

	}



}
