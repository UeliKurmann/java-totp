package dev.samstevens.totp.util;

public class Base32 {

    public static byte[] decode(String s){
        return CustomBase32.decode(s.getBytes());
    }

    public static String encode(byte[] b){
        return new String(CustomBase32.encode(b));
    }
}
