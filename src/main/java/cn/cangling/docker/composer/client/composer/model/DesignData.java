package cn.cangling.docker.composer.client.composer.model;

import jsinterop.annotations.JsType;

@JsType
public class DesignData {
    public double x;
    public double y;

    public void setData(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
