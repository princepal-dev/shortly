package com.princeworks.shortify.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClickEventController {
    @GetMapping("/{code}")
    @Cacheable(value = "urlCache", key = "#code")
    public String redirectToOriginalUrl(@PathVariable String code) {

    }
}
