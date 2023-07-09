package com.videoChat.back.utils;

import com.google.gson.Gson;
import com.videoChat.back.cache.TokenCache;
import com.videoChat.back.entity.User;
import com.videoChat.back.entityVo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class JwtGenerator {
  @Value("${harry.jwt.secret}")
  private String secret;

  @Value("${harry.jwt.expMillis}")
  private Long expMillis;


  @Autowired
  private TokenCache tokenCache;
  // 生成token
  public String generateToken(Authentication authentication) {
    Gson gson = new Gson();
    UserVo userVo = new UserVo();
    User userInfo = (User) authentication.getCredentials();
    userVo.setExpMillis(System.currentTimeMillis() + expMillis);
    userVo.setId(userInfo.getId());
    MacSigner macSigner = new MacSigner(secret);
    Jwt jwt = JwtHelper.encode(gson.toJson(userVo, UserVo.class), macSigner);
    return jwt.getEncoded();
  }

  public UserVo getUserFromJwt(String token) {
    try {
      Jwt jwt = JwtHelper.decode(token);
      String claims = jwt.getClaims();
      Gson gson = new Gson();
      UserVo user = gson.fromJson(claims, UserVo.class);
      return user;
    } catch (Exception e) {
      return null;
    }
  }

  public boolean validateToken(String token) {
    try {
      Jwt jwt = JwtHelper.decode(token);
      String claims = jwt.getClaims();
      Gson gson = new Gson();
      UserVo user = gson.fromJson(claims, UserVo.class);
      if (user.getExpMillis() < System.currentTimeMillis()) {
        throw new BadCredentialsException("token过期, 请重新登录");
      }

      String cacheToken = tokenCache.getTokenByUserId(user.getId());
      if (!StringUtils.hasText(cacheToken) || !token.equals(cacheToken)) {
        throw new BadCredentialsException("token失效, 请重新登录");
      }
      return true;
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        throw new AuthenticationCredentialsNotFoundException(e.getMessage());
      } else {
        throw new AuthenticationCredentialsNotFoundException("token错误");
      }

    }
  }
}
