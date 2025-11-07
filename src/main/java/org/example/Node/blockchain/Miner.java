package org.example.Node.blockchain;

import org.example.Node.blockchain.Models.Block;

public class Miner {
    public static int DIFFICULTY = 1;

    public static Block mineBlock(Block block){
        block.nonce = 0L;
        String hash = "";
        while (!hash.startsWith("0".repeat(DIFFICULTY))){
            block.nonce += 1;
            hash = Validation.getBlockHash(block);
            IO.println("nonce - " + block.nonce);
        }
        block.hash = hash;
        return block;
    }
}
