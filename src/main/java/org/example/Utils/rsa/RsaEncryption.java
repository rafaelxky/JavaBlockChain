package org.example.Utils.rsa;

import org.example.Utils.Bytes.Utf8;

import java.security.*;

public class RsaEncryption {

    public static byte[] rsaEncrypt(byte[] message, PrivateKey privateKey){
        try {
            var signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message);
            return signature.sign();
        } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] rsaEncrypt(String message, PrivateKey privateKey){
        return rsaEncrypt(Utf8.stringToBytes(message), privateKey);
    }

    public static boolean rsaVerify(byte[] messageBytes, byte[] signatureBytes, PublicKey publicKey) {
        try {
            if (publicKey == null){
                return false;
            }
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(messageBytes);
            return signature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
