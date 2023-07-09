package com.videoChat.back.config;

import com.videoChat.back.handler.AloneWebSocketHandler;
import com.videoChat.back.handler.UpdateWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


// @ServerEndpoint 注解方式使用websocket

// 配置模式

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired
  private AloneWebSocketHandler aloneWebSocketHandler;

  @Autowired
  private UpdateWebSocketHandler updateWebSocketHandler;
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
            .addHandler(aloneWebSocketHandler, "/chat-alone/{token}")
            .addHandler(updateWebSocketHandler, "/update-message/{token}")
            .setAllowedOrigins("*");
  }
}
