package com.videoChat.back.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
  public static String genMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(str.getBytes("utf-8"));
    byte[] md5Byte = md5.digest();
    return ByteUtil.bytes2Hex(md5Byte);
  }

  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    System.out.println(genMD5("xqs933131"));
  }
}
