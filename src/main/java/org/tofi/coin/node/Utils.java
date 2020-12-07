package org.tofi.coin.node;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    public static String byteArrayToHex(byte[] array){
        StringBuilder result = new StringBuilder(2 * array.length);
        for (byte b : array) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                result.append('0');
            }
            result.append(hex);
        }
        return result.toString();
    }

    public static String getMerkleRoot(String[] array){
        ArrayList<String> hashes = new ArrayList<>();

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }

        for(String string : array){
            byte[] encodedhash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            hashes.add(Utils.byteArrayToHex(encodedhash));
        }

        while (hashes.size() > 1){
            for(int i = 0; i < hashes.size() / 2; i++){
                String concatedStrings = hashes.get(i * 2) + hashes.get(i * 2 + 1);
                byte[] encodedhash = digest.digest(concatedStrings.getBytes(StandardCharsets.UTF_8));
                hashes.set(i, Utils.byteArrayToHex(encodedhash));
            }

            if (hashes.size() % 2 == 1) {
                hashes.set(hashes.size() / 2, hashes.get(hashes.size() - 1));
                hashes.subList(hashes.size()/ 2 + 1,hashes.size()).clear();
            }else{
                hashes.subList(hashes.size()/ 2,hashes.size()).clear();
            }
        }

        if (hashes.isEmpty()) {
            return Utils.byteArrayToHex(digest.digest("".getBytes()));
        }
        return hashes.get(0);
    }
}
