package com.jstatic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5校验
 *
 * @author 谢俊权
 * @create 2016/2/16 16:56
 */
public class MD5Util {

    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

    private static final String EMPTY_STRING = "";

    private static char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String getMD5(File file){

        String content = FileUtil.read(file);
        return getMD5(content.getBytes());
    }
    public static String getMD5(byte[] source){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] temp = md.digest(source);
            return byteToHexString(temp);
        } catch (NoSuchAlgorithmException e) {
            logger.error("failed to getMD5", e);
        }
        return EMPTY_STRING;
    }

    private static String byteToHexString(byte[] temp){

        char[] str = new char[temp.length * 2];
        int k = 0;
        for(int i = 0; i < temp.length; i++){
            byte b = temp[i];
            str[k++] = hexDigits[b >>> 4 & 15];
            str[k++] = hexDigits[b & 15];
        }
        return new String(str);
    }

}
