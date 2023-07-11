package com.videoChat.back.entityVo;

import com.videoChat.back.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {
  private Long expMillis;
  private String verifyCode;
  private String verifyKey;
}
