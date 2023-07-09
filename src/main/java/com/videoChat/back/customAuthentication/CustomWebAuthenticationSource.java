package com.videoChat.back.customAuthentication;

import com.videoChat.back.utils.JwtGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class CustomWebAuthenticationSource implements AuthenticationDetailsSource<HttpServletRequest, CustomWebAuthenticationDetails> {

  @Override
  public CustomWebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new CustomWebAuthenticationDetails(context);
  }
}
