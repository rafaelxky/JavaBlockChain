package org.example.Node.blockchain.Validation;

import org.example.Node.blockchain.Chain.Chain;
import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Node.blockchain.Persistence.TransactionPool.ITransactionPoolRepository;
import org.example.Utils.Bytes.Base64;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.Sha.Sha;

public class BlockValidator implements IBlockValidator{

    private ITransactionValidator transactionValidator;
    private IBlockChainRepository blockChainRepository;

    public BlockValidator(){}

    public void setTransactionValidator(ITransactionValidator transactionValidator){
        this.transactionValidator = transactionValidator;
    }
    public void setBlockChainRepository(IBlockChainRepository blockChainRepository){
        this.blockChainRepository = blockChainRepository;
    }

    public String getBlockHash(Block block){
        var blockData = block.getData() + block.getNonce();
        var blockHash = Base64.base64BytesToString(Sha.sha256Encrypt(Utf8.stringToBytes(blockData)));
        IO.println("blockHash - " + blockHash);
        return blockHash;
    }

    public boolean blockHashCompliesWithChallenge(String blockHash){
        return blockHash.startsWith("0".repeat(Miner.DIFFICULTY));
    }

    public boolean isBlockHashValid(String blockHash){
        if (blockHash == null) {
            IO.println("Block invalid: block hash is null");
            return false;
        }
        if (!blockHashCompliesWithChallenge(blockHash)){
            IO.println("Block invalid: block hash does not comply with challenge");
            return false;
        }
        return true;
    }

    public boolean doesBlockHashMatchData(Block block){
        return block.getHash().equals(getBlockHash(block));
    }

    public boolean isPreviousHashValid(String previousHash){
        return (previousHash == null || previousHash.equals(blockChainRepository.getLastBlock().hash));
    }

    public boolean isBlockValid(Block block){
        var blockHash = block.getHash();
        var previousHash = block.previousHash;

        if (!transactionValidator.areTransactionsValid(block.transactions)){
            IO.println("Invalid transactions in block");
            return false;
        }
        IO.println("transactions valid in block");

        if (!isBlockHashValid(blockHash)){
            IO.println("block hash invalid");
            return false;
        }
        IO.println("block hash valid");

        if(!isPreviousHashValid(previousHash)){
            IO.println("Block previous hash mismatch");
            return false;
        }
        if (!doesBlockHashMatchData(block)){
            IO.println("block hash invalid");
            return false;
        }

        IO.println("Block previous hash matches");
        return true;
    }
}
