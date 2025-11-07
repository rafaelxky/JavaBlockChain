package org.example.Node.blockchain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Validation.IBlockValidator;

public class Miner {
    public static int DIFFICULTY = 1;
    public IBlockValidator blockValidator;

    public Miner(){}

    public static void setDIFFICULTY(int DIFFICULTY) {
        Miner.DIFFICULTY = DIFFICULTY;
    }

    public void setBlockValidator(IBlockValidator blockValidator) {
        this.blockValidator = blockValidator;
    }

    public Block mineBlock(Block block){
        block.nonce = 0L;
        String hash = "";
        while (!hash.startsWith("0".repeat(DIFFICULTY))){
            block.nonce += 1;
            hash = blockValidator.getBlockHash(block);
            IO.println("nonce - " + block.nonce);
        }
        block.hash = hash;
        return block;
    }
}
