package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;



public class Driver {
    public Driver(String name) {
        this.name = name;
    }
    public Driver() {
        this("缺省驱动");
    }
    public String name;
    public String type;
    public String options;
    public String device;
}
