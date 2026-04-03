package com.princeworks.shortify.controller;

import com.princeworks.shortify.service.click.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {
  @Autowired
  private UrlService urlService;

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("API is Working fine");
  }

  @GetMapping("/{code}")
  public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String code) {
    String originalUrl = urlService.getOriginalUrl(code);
    return ResponseEntity.status(302).header("Location", originalUrl).build();
  }
}
