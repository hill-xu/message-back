package com.videoChat.back.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Message {
  private Integer from;

  private Integer to;

  private String message;
}
