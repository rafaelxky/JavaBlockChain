package org.example.Utils.Sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha {
    public static byte[] sha256Encrypt(byte[] text){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(text);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
