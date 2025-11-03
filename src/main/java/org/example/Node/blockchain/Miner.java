package org.example.Node.blockchain;

import org.example.Utils.Bytes.Base64;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.Sha.Sha;
import org.example.Utils.Validation;

import java.security.PublicKey;

public class Miner {
    public static int DIFFICULTY = 2;

    public static Block mineBlock(Block block){
        block.nonce = 0L;
        String hash = "";
        while (!hash.startsWith("0".repeat(DIFFICULTY))){
            block.nonce += 1;
            hash = Validation.getBlockHash(block);
            IO.println("nonce - " + block.nonce);
        }
        return block;
    }
}
