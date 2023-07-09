package com.videoChat.back.utils;

import com.videoChat.back.enumFactory.ReturnCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 拦截请求参数不符合的异常 @Validated
@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResJson<String> exception(Exception e) {
    return ResJson.fail(ReturnCode.RC500.getCode(), e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResJson<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    Map<String,String> map=new HashMap<>();
    List<FieldError> list=e.getBindingResult().getFieldErrors();
    for (FieldError error : list) {
      //参数的名称和错误信息
      map.put(error.getField(),error.getDefaultMessage());
    }
    return ResJson.fail(ReturnCode.RC500.getCode(), map.toString());
  }
}
