package com.princeworks.shortify.controller;

import com.princeworks.shortify.config.AppConstants;
import com.princeworks.shortify.dto.response.UrlDTO;
import com.princeworks.shortify.dto.response.UrlResponse;
import com.princeworks.shortify.entity.User;
import com.princeworks.shortify.service.click.UrlService;
import com.princeworks.shortify.util.AuthUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/urls")
public class UrlController {
  @Autowired private AuthUtil authUtil;
  @Autowired private UrlService urlService;

  @PostMapping("/shorten")
  public ResponseEntity<UrlDTO> createShortUrl(String originalUrl) {
    User user = authUtil.loggedInUser();
    UrlDTO urlDTO = urlService.createUrl(originalUrl, user);
    return ResponseEntity.ok(urlDTO);
  }

  @GetMapping("/{code}")
  public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String code) {
    String originalUrl = urlService.getOriginalUrl(code);
    return ResponseEntity.status(302).header("Location", originalUrl).build();
  }

  @GetMapping("/my")
  public ResponseEntity<UrlResponse> getAllUrls(
      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
          Integer pageNumber,
      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
          Integer pageSize) {
    User loggedInUser = authUtil.loggedInUser();
    UrlResponse urls = urlService.getAllUrls(loggedInUser, pageNumber, pageSize);
    return ResponseEntity.ok(urls);
  }

  @DeleteMapping("/{urlId}")
  public ResponseEntity<String > deleteUrl(@PathVariable Long urlId) {
    User loggedInUser = authUtil.loggedInUser();
    String status = urlService.deleteUrl(urlId, loggedInUser);
    return ResponseEntity.ok(status);
  }

  // TODO: Update a URL
}
