package org.example.Node.blockchain.Validation;

import org.example.Node.blockchain.Models.Block;

public interface IBlockValidator {
    public boolean isBlockValid(Block block);
    public String getBlockHash(Block block);
}
