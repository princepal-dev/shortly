package com.princeworks.shortify.service.click;

import com.princeworks.shortify.dto.response.UrlDTO;
import com.princeworks.shortify.dto.response.UrlResponse;
import com.princeworks.shortify.entity.User;

public interface UrlService {
    String getOriginalUrl(String code);
    UrlDTO createUrl(String originalUrl, User user);
    String deleteUrl(Long urlId, User loggedInUser);
    UrlResponse getAllUrls(User loggedInUser, Integer pageNumber, Integer pageSize);
}
