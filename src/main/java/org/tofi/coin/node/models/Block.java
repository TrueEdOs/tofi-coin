package org.tofi.coin.node.models;

public class Block {
    private BlockHeader blockHeader;
    private GameSolution solution;
    private Transaction[] transactions;

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public GameSolution getSolution() {
        return solution;
    }

    public void setSolution(GameSolution solution) {
        this.solution = solution;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
}
