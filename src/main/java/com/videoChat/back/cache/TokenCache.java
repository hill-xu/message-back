package com.videoChat.back.cache;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Data
public class TokenCache {
  private HashMap<Integer, String> tokenMap = new HashMap<>();

  public void addTokenByUserId(Integer key, String token) {
    this.tokenMap.put(key, token);
  }

  public void removeTokenByUserId(Integer key) {
    this.tokenMap.remove(key);
  }

  public String getTokenByUserId(Integer key) {
    return this.tokenMap.get(key);
  }
}
