package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;

public class DriverObject  extends RendingObject<Driver>{


    public DriverObject(Driver refObject) {
        super();
        setRefObject(refObject);
    }
    public DriverObject() {
        super();
        setRefObject(new Driver("---"));
    }
    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        Rect rect = getRect();
        context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("red");
        context2D.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
