package org.example.Node.blockchain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Services.ValidationServices.IBlockValidator;

public class Miner {
    public static int DIFFICULTY = 1;
    public IBlockValidator blockValidator;

    public Miner(IBlockValidator blockValidator) {
        this.blockValidator = blockValidator;
    }

    public static void setDIFFICULTY(int DIFFICULTY) {
        Miner.DIFFICULTY = DIFFICULTY;
    }

    public void setBlockValidator(IBlockValidator blockValidator) {
        this.blockValidator = blockValidator;
    }

    public Block mineBlock(Block block){
        var nonce = 0L;
        String hash = "";
        while (!hash.startsWith("0".repeat(DIFFICULTY))){
            nonce += 1;
            hash = blockValidator.getBlockHash(block, nonce);
            IO.println("nonce - " + nonce);
        }

        block.setHash(hash);
        block.setNonce(nonce);

        return block;
    }
}
