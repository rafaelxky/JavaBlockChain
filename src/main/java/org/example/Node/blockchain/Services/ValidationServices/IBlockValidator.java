package org.example.Node.blockchain.Services.ValidationServices;

import org.example.Node.blockchain.Models.Block;

public interface IBlockValidator {
    public boolean isBlockValid(Block block);
    public String getBlockHash(Block block, Long nonce);
}
