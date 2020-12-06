package org.tofi.coin.node.services;

import org.springframework.stereotype.Component;
import org.tofi.coin.node.Utils;
import org.tofi.coin.node.models.Block;
import org.tofi.coin.node.models.BlockHeader;
import org.tofi.coin.node.models.Transaction;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

@Component
public class Verifier {
    public static boolean verifyTransaction(Transaction tr) throws Exception {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(tr.getPublicKey().getBytes());

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        String message = tr.getSender() + tr.getReceiver() + tr.getAmount() + tr.getFee();
        ecdsaVerify.update(message.getBytes(StandardCharsets.UTF_8));

        boolean result = ecdsaVerify.verify(tr.getSignature().getBytes());
        return true;
    }

    public static boolean verifyBlock(Block block, String hash) throws Exception {
        BlockHeader header = block.getBlockHeader();
        String message = header.getNumber()
                + header.getPrevBlockHash()
                + header.getMerkleRootOfTransactions()
                + header.getMerkleRootOfBalances()
                + header.getTimestamp()
                + header.getAddressOfSolver()
                + header.getScore()
                + block.getSolution().toString();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

        String blockHash = Utils.byteArrayToHex(encodedhash);

        if (!hash.equals(blockHash)) {
            return false;
        }

        Transaction[] transactions = block.getTransactions();
        ArrayList<String> transactionsStrings = new ArrayList<>();

        for(Transaction transaction : transactions)
            transactionsStrings.add(transaction.toString());

        if (header.getMerkleRootOfTransactions().equals(Utils.getMerkleRoot(transactionsStrings.toArray(new String[0])))){
            return false;
        }

        return true;
    }

}
