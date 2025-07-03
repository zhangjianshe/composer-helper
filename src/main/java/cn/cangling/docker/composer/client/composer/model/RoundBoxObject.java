package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;

public class RoundBoxObject extends RendingObject {
    public double width;
    double radius;
    private double[] dash = null;
    private String color = "black";

    public RoundBoxObject() {
        this(10);
    }

    public RoundBoxObject(int radius) {
        this.radius = radius;
    }

    public RendingObject setDash(double[] dash) {
        this.dash = dash;
        return this;
    }

    public RendingObject setWidth(double width) {
        this.width = width;
        return this;
    }

    public RendingObject setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        context2D.save();
        context2D.beginPath();
        if (dash != null) {
            context2D.setLineDash(dash);
        }
        else {
            context2D.setLineDash(new double[]{});
        }
        context2D.setLineWidth(width);
        context2D.strokeColor = color;
        context2D.fillStyle = BaseRenderingContext2D.FillStyleUnionType.of(fillColor);

        Rect drawingRect = getDrawingRect();


        if (radius <= 0) {
            context2D.rect(drawingRect.x, drawingRect.y, drawingRect.width, drawingRect.height);
        } else {
            roundRect(context2D, drawingRect.x, drawingRect.y, drawingRect.width, drawingRect.height, radius);
        }
        context2D.stroke();
        if(fillColor!=null && !fillColor.isEmpty()) {
            context2D.fill();
        }

        drawSelectedBorder(context2D,t,5);
        context2D.restore();
    }

    String fillColor="";
    public void setFill(String fillStyle) {
        fillColor=fillStyle;
    }
}
