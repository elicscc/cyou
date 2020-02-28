package com.ccstay.cyou.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
@Component
public class BCryptPasswordEncoder {
    public static final String KEY_MD5 = "MD5";

    public  String encode(String password) {

        //System.out.println("=======加密前的数据:"+inputStr);
        BigInteger bigInteger=null;

        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = password.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {e.printStackTrace();}
       // System.out.println("MD5加密后:" + bigInteger.toString(16));
        return bigInteger.toString(16);
    }


    public boolean matches(String password, String password1) {

        if (encode(password).equals(password1)){
            return true;
        }
        return false;
    }
}
