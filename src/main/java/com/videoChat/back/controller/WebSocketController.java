package com.videoChat.back.controller;

import com.videoChat.back.utils.WebSocketSessionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webSocket")
public class WebSocketController {
  @GetMapping(value = "/closeAlone")
  public void close(@RequestParam String id) {
    WebSocketSessionManager.removeAndClose_chat_alone(id);
  }
}
