package com.videoChat.back.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

  @PostMapping(value = "/uploadImg")
  public String uploadImg(MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    file.transferTo(new File("/Users/qunshan/Desktop/" + fileName));
    return "/Users/qunshan/Desktop/" + fileName;
  }
}
