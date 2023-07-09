package com.videoChat.back.utils;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class RSAUtils {
  public static void main(String[] args) throws Exception {
//    genKeyPair();
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNZiKhQ5FfIWIhUmgydo8hMu3UiM4nICIVdqUSTiPzHCJ8PhnfBXONa50AshD0XEyZ/CMk1kAyiqt3OBnrQmeo6n33TrxFAB7IvUgxj6i2Az6aEhdleZZpsagLPfl/+3RMjyCqdpiYFW7gw4tKiPJeSrVSmeEZQu9Ij9rmEErKRQIDAQAB";
    //打印出来自己记录下
    System.out.println("公钥:" + publicKey);
    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM1mIqFDkV8hYiFSaDJ2jyEy7dSIzicgIhV2pRJOI/McInw+Gd8Fc41rnQCyEPRcTJn8IyTWQDKKq3c4GetCZ6jqffdOvEUAHsi9SDGPqLYDPpoSF2V5lmmxqAs9+X/7dEyPIKp2mJgVbuDDi0qI8l5KtVKZ4RlC70iP2uYQSspFAgMBAAECgYAq7hTBW3e8KCRUPh7Yo0aVpAkpAaIC8/ijv01PpuTlKGV9zdYG8uL8tTgWv+ECzfLVwWkQBzbaSXj+nUtvjEzRVaeKAyTP4FmlwwIAVRjjMIOvOeMv0hfI1iw3RcOg2w2oqFsYCndfUg+EmxuZOVUFQdlcC1H81HATbEmqMB3dkQJBANwFszAkr200JE1scHU0aB4tW5i7aEQFQMVj/gDeTtVTYoYi0MQKb9MKGg85LlYJIr+piOUcIGUqA2ThKPZbfFUCQQDu/Er3Nmxk1U536JZh9bLB8E3zuUzmQeeqawnVPPhxSEF1UG44LOSvZGA9DUHymzZ9euzcoNMM0omWP/pSZgYxAkEAjMuQ+RPgto8RVAI6jKX2oqj/3mK+vBhodGgiWMfpxpVPjOqmkWzkVjY2qts6jY/XDrMciRBQoqwZ8+op3kx46QJBAL76dp3fq/dYToCcKbDWu6cn9eNSrRVEjXEuYIk7U/6TE1xAjhPuLEHNIOso8Q6C4qSb2zTkgEO666BaOBwo08ECQG9lULRqbC8y2yvS0BG3M8r8NB8hhlxb2U9vetJy47xj2wzxbOza3jLdsRN3MSagW7mKD2N6c6P18sJY90ZZqNg=";
    //打印出来自己记录下
    System.out.println("私钥:" + privateKey);



    String orgData = "xqs933131";
    System.out.println("原数据：" + orgData);

    //加密
    String encryptStr =encrypt(orgData,publicKey);
    System.out.println("加密结果：" + encryptStr);

    //解密
    String decryptStr = decrypt(encryptStr,privateKey);
    System.out.println("解密结果：" + decryptStr);

  }

  private static final int KEY_SIZE = 1024;

  private static HashMap<Integer, String> keyMap = new HashMap<>();

  //编码返回字符串
  public static String encryptBASE64(byte[] key) throws Exception {
    return Base64.getEncoder().encodeToString(key);
  };

  //解码返回byte
  public static byte[] decryptBASE64(String key) throws Exception {
    return Base64.getDecoder().decode(key);
  };

  // RSA公钥加密
  public static String encrypt(String str, String publicKey) throws Exception {
    byte[] decoded = decryptBASE64(publicKey);
    RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    String outStr = encryptBASE64(cipher.doFinal(str.getBytes("UTF-8")));
    return outStr;
  };
  // RSA私钥解密
  public static String decrypt(String str, String privateKey) throws Exception {
    //64位解码加密后的字符串
    byte[] inputByte = decryptBASE64(str);
    //base64编码的私钥
    byte[] decoded = decryptBASE64(privateKey);
    RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    //RSA解密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, priKey);
    String outStr = new String(cipher.doFinal(inputByte));
    return outStr;
  }

  public static void genKeyPair() throws Exception {
    if (keyMap.size() == 0) {
      // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      // 初始化密钥对生成器
      keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
      // 生成一个密钥对，保存在keyPair中
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      // 得到私钥
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      // 得到公钥
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      String privateKeyStr = encryptBASE64(privateKey.getEncoded());
      String publicKeyStr = encryptBASE64(publicKey.getEncoded());

      keyMap.put(0, publicKeyStr);
      keyMap.put(1, privateKeyStr);
    }
  }
}
