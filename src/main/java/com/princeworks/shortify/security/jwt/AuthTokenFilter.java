package com.princeworks.shortify.security.jwt;

import com.princeworks.shortify.security.service.UserServiceDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired private JwtUtils jwtUtils;
  @Autowired private UserServiceDetailsImpl userServiceDetails;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    logger.debug("AuthTokenFilter: Processing request to {}", request.getRequestURI());

    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userServiceDetails.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.debug("Authentication set for user: {}", username);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  public String parseJwt(HttpServletRequest request) {
    String jwtFromCookie = jwtUtils.getJwtFromCookies(request);
    if (jwtFromCookie != null) return jwtFromCookie;
    return jwtUtils.getJwtFromHeader(request);
  }
}