package com.videoChat.back.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/upload")
public class AssetsController {

  @Value("${spring.web.resources.static-locations}")
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

  @PostMapping(value = "/downloadAssets")
  public void DownloadAssets(HttpServletResponse response, @RequestParam String path) throws IOException {
    File file = new File(path);
    String fileName = file.getName();
    FileInputStream fileInputStream = new FileInputStream(file);
    response.reset();
    response.setCharacterEncoding("UTF-8");
    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    response.addHeader("Content-Length", "" + file.length());
    response.setContentType("application/octet-stream");
    OutputStream outputStream = response.getOutputStream();
    fileInputStream.transferTo(outputStream);
    outputStream.flush();
    outputStream.close();
  }
}
