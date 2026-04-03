package com.princeworks.shortify.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @Email(message = "Please enter a valid email!")
    @NotBlank(message = "Anyone of the fields is missing")
    private String email;

    @NotBlank(message = "Anyone of the fields is missing")
    private String userName;

    @NotBlank(message = "Anyone of the fields is missing")
    private String password;
}
