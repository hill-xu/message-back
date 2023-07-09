package com.videoChat.back.customAuthentication;

import com.videoChat.back.entityVo.UserVo;
import com.videoChat.back.utils.JwtGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

  private UserVo userInfo;

  CustomWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    JwtGenerator jwtGenerator = new JwtGenerator();
    String token = request.getHeader("Authorization");
    UserVo userInfo = jwtGenerator.getUserFromJwt(token);
    this.userInfo = userInfo;
  }

  public UserVo getUserInfo() {
    return this.userInfo;
  }
}
