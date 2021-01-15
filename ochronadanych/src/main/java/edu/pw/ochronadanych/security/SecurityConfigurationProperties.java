package edu.pw.ochronadanych.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;
@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityConfigurationProperties {
	private List<String> allowedOrigins;
}
