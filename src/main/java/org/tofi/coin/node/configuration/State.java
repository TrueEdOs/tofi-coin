package org.tofi.coin.node.configuration;

import org.tofi.coin.node.models.Block;

import java.util.List;

public class State {
    private int blocksProceeded;
    private List<Block> proceededBlocks;

    public int getBlocksProceeded() {
        return blocksProceeded;
    }

    public void setBlocksProceeded(int blocksProceeded) {
        this.blocksProceeded = blocksProceeded;
    }

    public List<Block> getProceededBlocks() {
        return proceededBlocks;
    }

    public void setProceededBlocks(List<Block> proceededBlocks) {
        this.proceededBlocks = proceededBlocks;
    }
}
