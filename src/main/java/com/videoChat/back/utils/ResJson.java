package com.videoChat.back.utils;

import com.videoChat.back.enumFactory.ReturnCode;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResJson<T> {
  private Integer code = 200;

  private T data;

  private String message = "请求成功";

  private Long timestamp;

  public ResJson() {
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> ResJson<T> success(T data) {
    ResJson resJson = new ResJson();
    resJson.setCode(ReturnCode.RC100.getCode());
    resJson.setMessage(ReturnCode.RC100.getMessage());
    resJson.setData(data);
    return resJson;
  }

  public static <T> ResJson<T> fail(int code, String message) {
    ResJson resJson = new ResJson();
    resJson.setCode(code);
    resJson.setMessage(message);
    return resJson;
  }
}
