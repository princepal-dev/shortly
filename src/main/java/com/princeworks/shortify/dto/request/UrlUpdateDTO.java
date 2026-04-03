package com.princeworks.shortify.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlUpdateDTO {
    @NotBlank(message = "Please send the update data")
    private Boolean isActive;
}
