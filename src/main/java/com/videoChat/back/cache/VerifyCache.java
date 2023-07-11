package com.videoChat.back.cache;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Data
public class VerifyCache {
  private HashMap<String, String> verifyCodeMap = new HashMap<>();

  public void addVerifyCodeByUniqueId(String uniqueId, String code) {
    verifyCodeMap.put(uniqueId, code);
  }

  public String getVerifyCodeByUniqueId(String uniqueId) {
    return verifyCodeMap.get(uniqueId);
  }

  public void removeVerifyCodeByUniqueId(String uniqueId) {
    verifyCodeMap.remove(uniqueId);
  }

}
