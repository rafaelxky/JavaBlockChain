package org.example.Node.blockchain.Services.ValidationServices;

import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Utils.Bytes.Base64;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.Sha.Sha;

import java.util.List;

public class BlockValidator implements IBlockValidator{

    private ITransactionValidator transactionValidator;

    public BlockValidator(ITransactionValidator transactionValidator){
        this.transactionValidator = transactionValidator;
    }

    public String getBlockHash(Block block, Long nonce){
        var blockData = block.getData() + nonce;
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
        return block.getHash().equals(getBlockHash(block, block.getNonce()));
    }

    public boolean isPreviousHashValid(String previousHash, String previousBlockHash){
        return (previousHash == null || previousHash.equals(previousBlockHash));
    }

    @Override
    public boolean isBlockValid(
            Block block,
            String previousBlockHash,
            List<Transaction> transactionPool,
            List<Block> blockChain,
            List<Integer> emittersBalance
    ){
        var blockHash = block.getHash();
        var previousHash = block.getPreviousHash();

        if (!transactionValidator.areTransactionsValid(
                block.getTransactions(),
                transactionPool,
                blockChain,
                emittersBalance
        )){
            IO.println("Invalid transactions in block");
            return false;
        }
        IO.println("transactions valid in block");

        if (!isBlockHashValid(blockHash)){
            IO.println("block hash invalid");
            return false;
        }
        IO.println("block hash valid");

        if(!isPreviousHashValid(previousHash, previousBlockHash)){
            IO.println("Block previous hash mismatch");
            IO.println( previousHash + " != " + previousBlockHash);
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
