package com.feng.util;
//commons-codec-1.6
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Encoder;

public class MD5{
    //Apache的DigestUtils方法commons-codec-1.6包，建议此种方法
    public static String EncoderPwdByApacheMd5(String password){
        return DigestUtils.md5Hex(password);
    }
    //java.security对外不公开不建议
    public static String EncoderPwdByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //这里使用sun的未公开的sun.misc.BASE64Encoder类
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        // 说明：MD5加密后的字节数组，再使用base64对其进行编码
        String newstr = base64en.encode(md5.digest(password.getBytes("utf-8")));
        return newstr;
    }
    //Apache的Base64
    public static String EncoderPwd(String password) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        // 加密后的字符串
        // 说明：MD5加密后的字节数组，再使用base64对其进行编码
        String newstr = Base64.encodeBase64String (md5.digest(password.getBytes("utf-8")));
        return newstr;
    }
    public static void main(String[] args) {
        System.out.println(EncoderPwdByApacheMd5("123"));
        try {
            System.out.println(EncoderPwdByMd5("123"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(EncoderPwd("123"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
