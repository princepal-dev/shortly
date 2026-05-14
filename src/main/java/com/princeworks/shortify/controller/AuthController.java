package com.princeworks.shortify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.princeworks.shortify.dto.request.LoginDTO;
import com.princeworks.shortify.dto.request.RegisterDTO;
import com.princeworks.shortify.dto.response.LoginResponse;
import com.princeworks.shortify.dto.response.MessageResponse;
import com.princeworks.shortify.dto.response.RegisterResponse;
import com.princeworks.shortify.entity.User;
import com.princeworks.shortify.repository.UserRepository;
import com.princeworks.shortify.security.jwt.JwtUtils;
import com.princeworks.shortify.security.service.UserDetailsImpl;
import com.princeworks.shortify.service.auth.AuthService;

@RestController("/api/auth")
public class AuthController {
  @Value("${shortify.app.jwtCookieName}")
  private String jwtCookieName;

  @Autowired
  private AuthService authService;

  @Autowired
  private JwtUtils jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody final RegisterDTO registerDTO) {
    final RegisterResponse registerResponse = authService.register(registerDTO);
    return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
  }

  public ResponseEntity<?> login(@RequestBody final LoginDTO loginDTO) {
    try {
      final Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      final User user = userRepository.findById(userDetails.getId())
          .orElseThrow(() -> new RuntimeException("User not found"));
      final ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);
      final String jwtToken = jwtUtil.generateTokenFromUsername(userDetails.getUsername());

      return ResponseEntity.ok()
          .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
          .body(new LoginResponse(user.getEmail(), user.getUserName(), jwtToken, user.getCreatedAt()));
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    final ResponseCookie cookie = jwtUtil.getCleanJwtCookie();
    return ResponseEntity.ok().header(jwtCookieName, cookie.toString()).body("You've been signed out!");
  }
}
