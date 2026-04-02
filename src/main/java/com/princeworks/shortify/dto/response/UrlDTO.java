package com.princeworks.shortify.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlDTO {
    private String shortUrl;
    private String originalUrl;

    public UrlDTO(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }
}
