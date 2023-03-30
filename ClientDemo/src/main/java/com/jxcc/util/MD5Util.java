package com.jxcc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author dingyb
 * @Date 2023/3/2
 * @Description
 */
public class MD5Util {
    public static String getMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String getMD5(String[] inputs) throws NoSuchAlgorithmException {
        String[] md5Values = new String[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            md5Values[i] = getMD5(inputs[i]);
        }
        String combined = String.join("", md5Values);
        return getMD5(combined);
    }
}
