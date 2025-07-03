package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;

public class DragBoxObject extends RendingObject<String> {
    Point start;
    Point end;
    Rect rect = new Rect();
    private final double[] DASH = new double[]{3, 3};

    public DragBoxObject() {
        start = new Point();
        end = new Point();
    }

    public void setStart(double x, double y) {
        start.set(x, y);
    }

    public void setEnd(double x, double y) {
        end.set(x, y);
    }

    public Rect getRect() {
        double x = Math.min(start.x, end.x);
        double y = Math.min(start.y, end.y);
        double w = Math.abs(start.x - end.x);
        double h = Math.abs(start.y - end.y);
        rect.set(x, y, w, h);
        return rect;
    }

    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        context2D.save();
        context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of("skyblue");
        context2D.fillStyle = BaseRenderingContext2D.FillStyleUnionType.of("#93aec033");
        context2D.beginPath();
        context2D.setLineWidth(1);
        context2D.setLineDash(DASH);
        Rect rect1 = getRect();
        context2D.rect(rect1.x, rect1.y, rect1.width, rect1.height);
        context2D.fill();
        context2D.stroke();
        context2D.restore();
    }
}
