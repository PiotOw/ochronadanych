package edu.pw.ochronadanych.controller;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import edu.pw.ochronadanych.entity.Role;
import edu.pw.ochronadanych.entity.User;
import edu.pw.ochronadanych.enums.ERole;
import edu.pw.ochronadanych.payload.request.LoginRequest;
import edu.pw.ochronadanych.payload.request.SignupRequest;
import edu.pw.ochronadanych.payload.response.JwtResponse;
import edu.pw.ochronadanych.payload.response.MessageResponse;
import edu.pw.ochronadanych.repository.RoleRepository;
import edu.pw.ochronadanych.repository.UserRepository;
import edu.pw.ochronadanych.security.jwt.JwtUtils;
import edu.pw.ochronadanych.security.services.LoginAttemptService;
import edu.pw.ochronadanych.security.services.UserDetailsImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LoginAttemptService loginAttemptService;

	@Value("${edu.app.apiResponseTimeoutSec}")
	private int apiTimeoutSec;


	@PostMapping("/signin")
	@SneakyThrows
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		TimeUnit.SECONDS.sleep(apiTimeoutSec);

		boolean blocked = loginAttemptService.isBlocked(loginRequest.getUsername());

		if (blocked) {
			final Map<String, Object> mapBodyException = new HashMap<>() ;
			mapBodyException.put("error"    , "Login error");
			mapBodyException.put("message"  , "Invalid login count for user " + loginRequest.getUsername() + " limit exceed! Please wait 10 minutes...");
			mapBodyException.put("timestamp", (new Date()).getTime()) ;
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapBodyException);
		}


		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws InterruptedException {

		TimeUnit.SECONDS.sleep(apiTimeoutSec);


		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
