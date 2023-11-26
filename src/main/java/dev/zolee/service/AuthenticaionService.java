package dev.zolee.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.zolee.config.JwtService;
import dev.zolee.dto.AuthenticationRequest;
import dev.zolee.dto.AuthenticationResponse;
import dev.zolee.dto.RegisterRequest;
import dev.zolee.entity.Role;
import dev.zolee.entity.User;
import dev.zolee.repository.UserRepository;

@Service
public class AuthenticaionService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticaionService(UserRepository userRepository, 
								PasswordEncoder passwordEncoder, 
								JwtService jwtService, 
								AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public ResponseEntity<?> register(RegisterRequest registerRequest) {
		User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()), Role.USER);
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken);
		return ResponseEntity.ok(authenticationResponse);
	}

	public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken);
		return ResponseEntity.ok(authenticationResponse);
	}
	
}
