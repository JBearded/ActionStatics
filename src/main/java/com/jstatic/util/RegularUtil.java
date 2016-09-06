package com.jstatic.util;

import java.util.regex.Pattern;

/**
 * 常用正则匹配
 *
 * @author 谢俊权
 * @create 2016/1/30 22:26
 */
public class RegularUtil {

    public static boolean isNumber(String str){
        return (isInteger(str) || isFloat(str));
    }

    public static boolean isInteger(String str){
        String regex = "^((\\d{1})|([1-9]+\\d+))$";
        return Pattern.matches(regex, str);
    }

    public static boolean isFloat(String str){
        String regex = "^((0{1})|([1-9]+\\d+))\\.\\d+$";
        return Pattern.matches(regex, str);
    }

    public static void main(String[] args){
        System.out.println(RegularUtil.isFloat(".5"));
    }
}



