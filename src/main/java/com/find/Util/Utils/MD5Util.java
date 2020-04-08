package com.find.Util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);
    /**
     * 将传入的字符串通过md5加密后，编码为base64格式
     * @param str 源字符串
     * @return 利用md5算法加密后的base64格式的字符串
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Str(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md5.digest(str.getBytes());
        logger.debug("MD5编码为：" + digest.toString());
        String result = Base64.getEncoder().encodeToString(digest);
        logger.debug("Base64编码为：" + result);
        return result;
    }
}
