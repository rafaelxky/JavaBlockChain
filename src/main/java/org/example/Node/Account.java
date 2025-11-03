package org.example.Node;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Account {
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public Account(KeyPair keyPair){
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }
}
