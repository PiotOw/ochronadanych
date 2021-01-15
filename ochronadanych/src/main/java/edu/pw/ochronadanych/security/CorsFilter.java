package edu.pw.ochronadanych.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
class CorsFilter extends OncePerRequestFilter {
	private final SecurityConfigurationProperties properties;
	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request,
									@NotNull HttpServletResponse response,
									@NotNull FilterChain filterChain) throws ServletException, IOException {
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", properties.getAllowedOrigins().contains(origin) ? origin : "");
		response.setHeader("Vary", "Origin");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "authorization, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		if (!"OPTIONS".equals(request.getMethod())) {
			filterChain.doFilter(request, response);
		}
	}
	public void destroy() {
	}
}
