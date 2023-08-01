package com.videoChat.back.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class AssetsController {

  @Value("${custom.assetsPath}")
  private String assetsPath;
  @PostMapping(value = "/uploadAssets")
  public String uploadAssets(@RequestBody MultipartFile file, @RequestParam String type) throws IOException {
    String fileName = file.getOriginalFilename();
    String dirPath = assetsPath + "/" + type;
    String filePath = dirPath + "/" + fileName;
    File dir = new File(dirPath);
    if (dir.exists()) {
      file.transferTo(new File(filePath));
    } else {
      dir.mkdirs();
      file.transferTo(new File(filePath));
    }
    return filePath;
  }
}
