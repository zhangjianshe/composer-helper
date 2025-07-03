package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.TextMetrics;

/**
 * 描述端口
 */
public class PortObject extends RendingObject<String>{
    public PortObject() {
        setRefObject("");
    }
    public PortObject(String port) {
        setRefObject(port);
    }
    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        String port=getRefObject();
        if(port!=null)
        {
            port=port.trim();
        }
        if(port==null || port.length()==0)
        {
            return;
        }
        String[] split = port.split(":", 2);
        String expose="";
        String mapper="";
        if(split.length==2)
        {
            expose=split[0];
            mapper=split[1];
        }
        else {
            expose=split[0];
            mapper="";
        }
        int radius=5;
        if(mapper.length()==0)
        {
            Rect drawingRect=getDrawingRect();
            Point center=drawingRect.center();
            // only export
            context2D.save();
            context2D.font="14px 'Microsoft YaHei'";
            TextMetrics m = context2D.measureText("M");
            double fontHeight=m.actualBoundingBoxAscent+m.actualBoundingBoxDescent;
            context2D.beginPath();
            context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("gray");
            context2D.arc(drawingRect.x+3,center.y,radius,0,Math.PI*2);
            context2D.fill();
            context2D.fillText(expose, drawingRect.x+drawingRect.width+4, center.y+fontHeight/2,120);
            context2D.restore();
        }
        else {
            Rect drawingRect=getDrawingRect();
            Point center=drawingRect.center();
            // only export
            context2D.save();
            context2D.font="14px 'Microsoft YaHei'";
            TextMetrics m = context2D.measureText("M");
            double fontHeight=m.actualBoundingBoxAscent+m.actualBoundingBoxDescent;

            context2D.beginPath();
            context2D.strokeStyle= BaseRenderingContext2D.StrokeStyleUnionType.of("orange");
            context2D.setLineWidth(3);
            context2D.moveTo(drawingRect.x+3,center.y);
            context2D.lineTo(drawingRect.x+drawingRect.width-3,center.y);
            context2D.stroke();

            context2D.beginPath();
            context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("orange");
            context2D.arc(drawingRect.x+3,center.y,radius,0,Math.PI*2);
            context2D.fill();

            context2D.beginPath();
            context2D.arc(drawingRect.x+drawingRect.width-3,center.y,radius,0,Math.PI*2);
            context2D.fill();

            context2D.fillText(expose+":"+mapper, drawingRect.x+drawingRect.width+4, center.y+fontHeight/2,120);
            context2D.restore();
        }
    }
}
