package cn.cangling.docker.composer.client.composer.model;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

@JsType
public class Network {
    public String name;
    public Boolean external;
    public String driver;
    public DesignData designData;

    @JsConstructor
    public Network(String name) {
        this.name = name;
        external = false;
        designData=new DesignData();
    }
}
