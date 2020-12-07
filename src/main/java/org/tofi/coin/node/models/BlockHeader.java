package org.tofi.coin.node.models;

public class BlockHeader {
    private int number;
    private String hash;
    private String prevBlockHash;
    private String merkleRootOfTransactions;
    private long timestamp;
    private String addressOfSolver;
    private int score;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevBlockHash() {
        return prevBlockHash;
    }

    public void setPrevBlockHash(String prevBlockHash) {
        this.prevBlockHash = prevBlockHash;
    }

    public String getMerkleRootOfTransactions() {
        return merkleRootOfTransactions;
    }

    public void setMerkleRootOfTransactions(String merkleRootOfTransactions) {
        this.merkleRootOfTransactions = merkleRootOfTransactions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAddressOfSolver() {
        return addressOfSolver;
    }

    public void setAddressOfSolver(String addressOfSolver) {
        this.addressOfSolver = addressOfSolver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append(number)
                .append(prevBlockHash)
                .append(merkleRootOfTransactions)
                .append(timestamp)
                .append(addressOfSolver)
                .append(score);

        return ret.toString();
    }
}
