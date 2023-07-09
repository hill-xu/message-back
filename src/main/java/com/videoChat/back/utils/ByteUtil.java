package com.videoChat.back.utils;

public class ByteUtil {
  private final static char[] HEX = "0123456789abcdef".toCharArray();

  public static String bytes2Hex(byte[] bys) {
    char[] chs = new char[bys.length * 2];
    for(int i = 0, offset = 0; i < bys.length; i++) {
      chs[offset++] = HEX[bys[i] >> 4 & 0xf];
      chs[offset++] = HEX[bys[i] & 0xf];
    }
    return new String(chs);
  }
}
