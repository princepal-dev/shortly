package com.princeworks.shortify.service.auth;

import com.princeworks.shortify.dto.request.RegisterDTO;
import com.princeworks.shortify.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterDTO registerDTO);
}
