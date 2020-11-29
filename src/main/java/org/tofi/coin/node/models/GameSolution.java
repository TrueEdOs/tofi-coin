package org.tofi.coin.node.models;

public class GameSolution {
    private int length;
    private int[] rotation;
    private int[] xAxis;
    private int[] yAxis;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getRotation() {
        return rotation;
    }

    public void setRotation(int[] rotation) {
        this.rotation = rotation;
    }

    public int[] getxAxis() {
        return xAxis;
    }

    public void setxAxis(int[] xAxis) {
        this.xAxis = xAxis;
    }

    public int[] getyAxis() {
        return yAxis;
    }

    public void setyAxis(int[] yAxis) {
        this.yAxis = yAxis;
    }
}
