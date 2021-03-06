package org.tofi.coin.node.models;

public class BlockHeader {
    private int number;
    private String prevBlockHash;
    private String merkleRootOfTransactions;
    private String merkleRootOfBalances;
    private int timestamp;
    private String addressOfSolver;
    private int score;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getMerkleRootOfBalances() {
        return merkleRootOfBalances;
    }

    public void setMerkleRootOfBalances(String merkleRootOfBalances) {
        this.merkleRootOfBalances = merkleRootOfBalances;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
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
                .append(merkleRootOfBalances)
                .append(timestamp)
                .append(addressOfSolver)
                .append(score);

        return ret.toString();
    }
}
