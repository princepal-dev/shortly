package com.princeworks.shortify.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class UrlUtil {
    public String generateShortUrl(String originalUrl) {
        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        return encoder.encodeToString(originalUrl.getBytes(StandardCharsets.UTF_8));
    }

    public String decodeShortUrl(String shortUrl) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(shortUrl);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
