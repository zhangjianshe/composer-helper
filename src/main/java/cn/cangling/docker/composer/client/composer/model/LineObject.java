package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.Path2D;

public class LineObject extends RendingObject<String> {
    Point source = new Point(0, 0);
    Point target = new Point(0, 0);

    double lineWidth=1;
    String strokeColor="";
    public void setStrokeColor(String color)
    {
        this.strokeColor=color;
    }
    public void setLineWidth(double width)
    {
        this.lineWidth=width;
    }
    double arrowLength=0;
    double arrowWidth=0;

    /**
     * 箭头的长度和宽度
     * @param length
     * @param width
     */
    public void setEndArrow(double length,double width)
    {
            this.arrowWidth=width;
            this.arrowLength=length;
    }
    public void setSource(double x, double y) {
        source.set(x, y);
    }

    public void setTarget(double x, double y) {
        target.set(x, y);
    }

    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        context2D.save();
            context2D.beginPath();
            context2D.setLineWidth(lineWidth);
            context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of(strokeColor);
            context2D.fillStyle = BaseRenderingContext2D.FillStyleUnionType.of(strokeColor);
            if(dash!=null) {
                context2D.setLineDash(dash);
            }
            context2D.moveTo(source.x, source.y);
            context2D.lineTo(target.x, target.y);
            context2D.stroke();
            if(arrowLength>0)
            {
                //绘制末端箭头
                Path2D path=new Path2D();
                Point[] arrowTriangle=Point.calculateArrowheadPoints(source,target,arrowLength,arrowWidth);
                if(arrowTriangle!=null) {
                    path.moveTo(arrowTriangle[0].x, arrowTriangle[0].y);
                    for (int i = 1; i < arrowTriangle.length; i++) {
                        path.lineTo(arrowTriangle[i].x, arrowTriangle[i].y);
                    }
                    path.closePath();
                    context2D.fill(path);
                }
            }
        context2D.restore();
    }
    private double[] dash;
    public void setDash(double[] dash) {
        this.dash = dash;
    }
}
