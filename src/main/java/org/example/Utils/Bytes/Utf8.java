package org.example.Utils.Bytes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utf8 {
    public static byte[] stringToBytes(String string){
        return string.getBytes(StandardCharsets.UTF_8);
    }
    public static String bytesToString(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
