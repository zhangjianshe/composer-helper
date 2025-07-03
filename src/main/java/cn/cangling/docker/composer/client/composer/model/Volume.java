package cn.cangling.docker.composer.client.composer.model;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

@JsType
public class Volume  {
    public String name;
    public String driver;
    public DesignData designData;
    @JsConstructor
    public Volume(String name) {
        this.name = name;
        designData=new DesignData();
    }

}
