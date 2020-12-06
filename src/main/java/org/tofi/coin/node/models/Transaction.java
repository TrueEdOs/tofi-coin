package org.tofi.coin.node.models;

public class Transaction {
    private String sender;
    private String receiver;
    private long amount;
    private long fee;
    private String publicKey;
    private String signature;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append(sender)
                .append(receiver)
                .append(amount)
                .append(fee)
                .append(publicKey)
                .append(signature);
        return ret.toString();
    }
}
