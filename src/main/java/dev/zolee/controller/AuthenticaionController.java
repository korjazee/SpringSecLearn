package dev.zolee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.zolee.dto.AuthenticationRequest;
import dev.zolee.dto.RegisterRequest;
import dev.zolee.service.AuthenticaionService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticaionController {

	private final AuthenticaionService authenticaionService;
	
	public AuthenticaionController(AuthenticaionService authenticaionService) {
		this.authenticaionService = authenticaionService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		return authenticaionService.register(registerRequest);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) {
		return authenticaionService.authenticate(authenticationRequest);
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
}
