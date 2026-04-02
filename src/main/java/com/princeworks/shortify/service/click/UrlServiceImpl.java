package com.princeworks.shortify.service.click;

import com.princeworks.shortify.dto.response.UrlDTO;
import com.princeworks.shortify.dto.response.UrlResponse;
import com.princeworks.shortify.entity.Url;
import com.princeworks.shortify.entity.User;
import com.princeworks.shortify.repository.UrlRepository;
import com.princeworks.shortify.util.UrlUtil;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
  @Autowired private UrlUtil urlUtil;
  @Autowired private UrlRepository urlRepository;

  @Override
  public UrlDTO createUrl(String originalUrl, User user) {
    if (originalUrl == null || originalUrl.isEmpty()) {
      throw new IllegalArgumentException("Original URL cannot be null or empty");
    }
    Url newUrl = new Url(user, originalUrl);
    String shortUrl = urlUtil.generateShortUrl(originalUrl);
    newUrl.setShortCode(shortUrl);
    newUrl.setIsActive(true);

    urlRepository.save(newUrl);
    return new UrlDTO(newUrl.getOriginalUrl(), newUrl.getShortCode());
  }

  @Override
  public String deleteUrl(Long urlId, User loggedInUser) {
    Url url =
        urlRepository
            .findById(urlId)
            .orElseThrow(() -> new IllegalArgumentException("URL not found with id: " + urlId));
    User user = url.getUser();

    if (!Objects.equals(loggedInUser.getId(), user.getId())) {
      throw new IllegalStateException("Unauthorized to delete this URL");
    }
    urlRepository.delete(url);
    return "URL deleted successfully with id: " + urlId;
  }

  @Override
  public String getOriginalUrl(String code) {
    Url url =
        urlRepository
            .findByShortCode(code)
            .orElseThrow(() -> new IllegalArgumentException("Invalid short code: " + code));
    if (!url.getIsActive()) {
      throw new IllegalStateException("URL is inactive");
    }
    return url.getOriginalUrl();
  }

  @Override
  public UrlResponse getAllUrls(User loggedInUser, Integer pageNumber, Integer pageSize) {
    Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
    Page<Url> urlPage = urlRepository.findByUserId(loggedInUser.getId(), pageable);
    List<Url> urls = urlPage.getContent();
    if (urls.isEmpty()) throw new IllegalStateException("No URLs found for the user");
    List<UrlDTO> urlDTOs =
        urls.stream().map(url -> new UrlDTO(url.getOriginalUrl(), url.getShortCode())).toList();

    UrlResponse urlResponse = new UrlResponse();
    urlResponse.setContent(urlDTOs);
    urlResponse.setPageNumber(urlPage.getNumber());
    urlResponse.setPageSize(urlPage.getSize());
    urlResponse.setTotalElements(urlPage.getTotalElements());
    urlResponse.setTotalPages(urlPage.getTotalPages());
    urlResponse.setLast(urlPage.isLast());
    return urlResponse;
  }
}
