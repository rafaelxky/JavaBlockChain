package org.example;

import org.example.Node.blockchain.*;
import org.example.Node.Account;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.rsa.RsaEncryption;
import org.example.Utils.rsa.RsaGeneration;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Chain chain = new Chain();
        IO.println("chain" + chain);

        Account account1 = new Account(RsaGeneration.generateRsaKeyPair());
        Account account2 = new Account(RsaGeneration.generateRsaKeyPair());
        Account maliciousAccount = new Account(RsaGeneration.generateRsaKeyPair());
        Account genesis = new Account(RsaGeneration.generateRsaKeyPairFromSeed(Utf8.stringToBytes("COUNTERESPIONAGE")));

        Random random = new Random();

        Transaction transaction = new Transaction(
                genesis.publicKey,
                account2.publicKey,
                100,
                random.nextInt()
        );

        var signature = RsaEncryption.rsaEncrypt(transaction.getData(), genesis.privateKey);
        transaction.sign(signature);

        Transaction transaction1 = new Transaction(
                account1.publicKey,
                maliciousAccount.publicKey,
                100,
                random.nextInt()
        );

        transaction1.sign(signature);

        IO.println("Transaction " + (chain.addTransactionToPool(transaction)?"was successfully":"was not successfully"));
        IO.println("Malicious transaction " + (chain.addTransactionToPool(transaction1)?"was not blocked":"was blocked"));

        var non_mined_block = chain.getNewBlock();
        IO.println("New block --------------------------------------------------- " + non_mined_block);
        var block = Miner.mineBlock(non_mined_block);

        var falseBlock = chain.getNewBlock();
        falseBlock.hash = block.hash;
        falseBlock.nonce = block.nonce;

        IO.println("BLOCK - " + block);
        IO.println("block added - " + (chain.addBlockToChain(block)?"successfully":"not successfully"));
        IO.println("fake block - " + (chain.addBlockToChain(falseBlock)?"not blocked":"blocked"));

        IO.println("Acc1 balance - " + chain.getBalance(account1.publicKey));
        IO.println("Acc2 balance - " + chain.getBalance(account2.publicKey));
        IO.println("Malicious balance - " + chain.getBalance(maliciousAccount.publicKey));
        IO.println("Acc gen balance - " + chain.getBalance(genesis.publicKey));
        IO.println("Acc gen public - " + genesis.publicKey);
        IO.println("Chain - " + chain);
    }
}