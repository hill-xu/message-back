package com.videoChat.back.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.videoChat.back.cache.TokenCache;
import com.videoChat.back.cache.VerifyCache;
import com.videoChat.back.customAuthentication.CustomWebAuthenticationDetails;
import com.videoChat.back.entity.User;
import com.videoChat.back.entityVo.UserVo;
import com.videoChat.back.service.impl.UserServiceImpl;
import com.videoChat.back.utils.JwtGenerator;
import com.videoChat.back.utils.MD5Utils;
import com.videoChat.back.utils.RSAUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Value("${rsa.privateKey}")
  private String privateKey;

  @Value("${rsa.publicKey}")
  private String publicKey;

  @Autowired
  private UserServiceImpl userServiceImpl;


  @Autowired
  private JwtGenerator jwtGenerator;

  @Autowired
  private TokenCache tokenCache;

  @Autowired
  private VerifyCache verifyCache;

  private AuthenticationManager authenticationManager = new AuthenticationManager() {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      return authentication;
    }
  };

  @GetMapping(value = "/genCode")
  public void genCode(HttpServletResponse response, @RequestParam String verifyKey) throws IOException {
    verifyCache.removeVerifyCodeByUniqueId(verifyKey);
    LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40);
    verifyCache.addVerifyCodeByUniqueId(verifyKey, lineCaptcha.getCode());
    System.out.println(lineCaptcha.getCode());
    lineCaptcha.write(response.getOutputStream());
    // 关闭流
    response.getOutputStream().close();
  }

  @GetMapping(value = "/getPublicKey")
  public String getPublicKey() {
    return publicKey;
  }

  @PostMapping(value = "/login")
  public String login(@RequestBody @Validated(User.login.class) UserVo user) throws Exception {
    try {
      String verifyCode = user.getVerifyCode();
      if (!StringUtils.hasText(verifyCode)) {
        throw new Exception("验证码不能为空");
      }
      if (verifyCode.equals(verifyCache.getVerifyCodeByUniqueId(verifyCode))) {
        throw new Exception("验证码不正确");
      }
      String encryptionPass = user.getPassword();
      String decryptPass = RSAUtils.decrypt(encryptionPass, privateKey);
      String md5Pass = MD5Utils.genMD5(decryptPass);
      LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<User>();
      userWrapper.eq(User::getUsername, user.getUsername()).eq(User::getPassword, md5Pass);
      User userInfo = userServiceImpl.getOne(userWrapper);
      if (userInfo != null) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        userInfo
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        tokenCache.addTokenByUserId(userInfo.getId(), token);
        verifyCache.removeVerifyCodeByUniqueId(user.getVerifyCode());
        return token;
      }
    } catch (Exception e) {
      if (e instanceof Exception) {
        throw new Exception(e.getMessage());
      }
      throw new Exception("密码解析异常");
    }
    throw new Exception("账号或密码错误");
  }

  @GetMapping(value = "logout")
  public String logout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
    Integer id = details.getUserInfo().getId();
    tokenCache.removeTokenByUserId(id);
    return "退出成功";
  }
}
