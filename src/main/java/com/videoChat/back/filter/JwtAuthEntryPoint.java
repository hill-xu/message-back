package com.videoChat.back.filter;

import com.google.gson.Gson;
import com.videoChat.back.enumFactory.ReturnCode;
import com.videoChat.back.utils.ResJson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    Gson gson = new Gson();
    String message = authException.getMessage();
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(ResJson.fail(ReturnCode.INVALID_TOKEN.getCode(), message)));
    response.getWriter().close();
  }
}
