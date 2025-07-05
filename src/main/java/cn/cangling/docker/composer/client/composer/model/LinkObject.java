package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.Path2D;

public class LinkObject extends RendingObject<Link> {
    private static final double[] DASH = new double[]{5,5};

    public LinkObject(Link link) {
        super();
        setRefObject(link);
        setVisible(false);
    }
    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        Link refObject = getRefObject();
        if(refObject!=null && refObject.start!=null && refObject.end!=null )
        {
            context2D.save();
            context2D.beginPath();
            if(isVisible()) {
                context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of("black");
                context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("black");
            }
            else {
                context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of("rgba(0,0,0,0.2)");
                context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("rgba(0,0,0,0.2)");
            }
            switch (refObject.kind)
            {
                case LINK_KIND_DEPENDENCY:
                    context2D.setLineDash(DASH);
                    context2D.setLineWidth(2);
                    break;
                case LINK_KIND_UNKNOWN:
                default:
                    context2D.setLineDash(new double[]{});
                    context2D.setLineWidth(1);
            }


            Point start,end;
            start= refObject.start.getLinkCenter(refObject.end,true);
            context2D.moveTo(start.x, start.y);

            end=refObject.end.getLinkCenter(refObject.start,false);
            context2D.lineTo(end.x, end.y);
            context2D.stroke();

            //在END端绘制一个箭头
            Path2D path=new Path2D();
            Point[] arrowTriangle=Point.calculateArrowheadPoints(start,end,10,5);
            path.moveTo(arrowTriangle[0].x, arrowTriangle[0].y);
            for(int i=1;i<arrowTriangle.length;i++)
            {
                path.lineTo(arrowTriangle[i].x, arrowTriangle[i].y);
            }
            path.closePath();

            context2D.fill(path);

            context2D.beginPath();
            context2D.arc(start.x,start.y,4,0,Math.PI*2);
            context2D.fill();
            context2D.restore();
        }
    }

    @Override
    public void offset(double dx, double dy) {
        // do nothing link object can not move by mouse drag
    }
}
