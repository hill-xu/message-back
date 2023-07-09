package com.videoChat.back.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Friends implements Serializable {
  private Integer selfId;

  private Integer friendId;
}
