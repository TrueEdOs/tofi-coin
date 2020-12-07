package org.tofi.coin.node.dto;

public class PushedResponse {
    private boolean pushed;

    public boolean isPushed() {
        return pushed;
    }

    public PushedResponse setPushed(boolean pushed) {
        this.pushed = pushed;
        return this;
    }
}
