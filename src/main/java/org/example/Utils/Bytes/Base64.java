package org.example.Utils.Bytes;

public class Base64 {
    // cannot contain spaces
    public static byte[] base64StringToBytes(String base64){
        return java.util.Base64.getDecoder().decode(base64);
    }
    public static String base64BytesToString(byte[] bytes){
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }
}
