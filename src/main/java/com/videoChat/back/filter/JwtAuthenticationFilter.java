package com.videoChat.back.filter;

import com.videoChat.back.config.GlobalConfig;
import com.videoChat.back.customAuthentication.CustomWebAuthenticationSource;
import com.videoChat.back.entity.User;
import com.videoChat.back.utils.JwtGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtGenerator jwtGenerator;
  @Autowired
  private CustomWebAuthenticationSource customWebAuthenticationSource;

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;



  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (Arrays.stream(GlobalConfig.safeUrls).anyMatch(path -> {
      return path.equals(request.getServletPath());
    })) {
      filterChain.doFilter(request, response);
    } else {
      try {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
          User user = jwtGenerator.getUserFromJwt(token);
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user, null);
          usernamePasswordAuthenticationToken.setDetails(customWebAuthenticationSource.buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
      } catch (AuthenticationException e) {
        SecurityContextHolder.clearContext();
        this.jwtAuthEntryPoint.commence(request, response, e);
      }
    }

  }
}
