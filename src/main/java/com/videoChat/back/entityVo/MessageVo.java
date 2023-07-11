package com.videoChat.back.entityVo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageVo {
  private Integer userId;

  private Integer friendId;

  private String messageType;

  private String text;

  private String resources;
}
