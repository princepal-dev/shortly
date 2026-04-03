package com.princeworks.shortify.service.auth;

import com.princeworks.shortify.dto.request.RegisterDTO;
import com.princeworks.shortify.dto.response.RegisterResponse;
import com.princeworks.shortify.entity.User;
import com.princeworks.shortify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public RegisterResponse register(RegisterDTO registerDTO) {
    if (userRepository.existsByEmail(registerDTO.getEmail()))
      throw new IllegalStateException("Email already registered, Kindly sign in!");

    if (userRepository.existsByUserName(registerDTO.getUserName()))
      throw new IllegalStateException("Username already exists, Kindly try something else");

    User newUser =
        new User(
            registerDTO.getUserName(),
            registerDTO.getEmail(),
            passwordEncoder.encode(registerDTO.getPassword()));

    userRepository.save(newUser);
    return new RegisterResponse(newUser.getEmail(), newUser.getUserName(), "Registration success!");
  }
}
