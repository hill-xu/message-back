package com.videoChat.back.config;

import com.videoChat.back.filter.JwtAuthenticationFilter;
import com.videoChat.back.filter.JwtAuthEntryPoint;
import com.videoChat.back.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// security 配置
@Configuration
@EnableWebSecurity
public class WebSecurityConfiger {

  //自定义异常认证处理
  private JwtAuthEntryPoint authEntryPoint;
  //自定义授权异常处理
  private MyAccessDeniedHandler myAccessDeniedHandler;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;


  @Autowired
  public WebSecurityConfiger(JwtAuthEntryPoint authEntryPoint, MyAccessDeniedHandler myAccessDeniedHandler) {
    this.authEntryPoint = authEntryPoint;
    this.myAccessDeniedHandler = myAccessDeniedHandler;
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            // 禁用默认登录页
            .formLogin(AbstractHttpConfigurer::disable)
            // 禁用默认登出页
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((auth) -> {
              auth
                      .requestMatchers(GlobalConfig.safeUrls)
                      .permitAll()
                      .requestMatchers("/update-message/**", "/chat-alone/**")
                      .permitAll()
                      .anyRequest()
                      .authenticated();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling((exceptionHandlingConfigurer) -> {
              exceptionHandlingConfigurer.accessDeniedHandler(myAccessDeniedHandler);
              exceptionHandlingConfigurer.authenticationEntryPoint(authEntryPoint);
            });
    return httpSecurity.build();
  }

}
