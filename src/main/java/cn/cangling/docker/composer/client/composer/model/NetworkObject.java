package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.TextMetrics;

import java.util.List;

public class NetworkObject extends RendingObject<Network> {
    public NetworkObject(Network network) {
        super();
        setRefObject(network);
    }
    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        if(isSelected()) {
            context2D.font = "bold 14px  'Microsoft YaHei'";
        }
        else {
            context2D.font = "14px  'Microsoft YaHei'";
        }
        context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("black");


        TextMetrics textMetrics = context2D.measureText(getRefObject().name);
        double boxWidth= 100;
        boxWidth=Math.max(textMetrics.width,boxWidth);
        int boxHeight=60;
        getRect().setHeight(boxHeight);
        getRect().setWidth(boxWidth);

        Rect drawingRect=getDrawingRect();
        double fontHeight= textMetrics.actualBoundingBoxAscent + textMetrics.actualBoundingBoxDescent;

        double startX=(boxWidth-textMetrics.width)/2;
        double startY=(boxHeight);

        double imageSize=60-fontHeight;
        imageSize=Math.min(imageSize,drawingRect.width);

        context2D.drawImage(Images.getNetRed(),drawingRect.x+(boxWidth-imageSize)/2,drawingRect.y,imageSize,imageSize);
        context2D.fillText(getRefObject().name,drawingRect.x + startX,drawingRect.y + startY,boxWidth);

        //选中边框
         drawSelectedBorder(context2D, t,0);
    }

    @Override
    public void setSelected(boolean selected) {
        if(selected)
        {
            //显示与这个服务连接的网络和卷
            List<LinkObject> links=getGraph().findTargetLinkObject(this);
            for (LinkObject linkObject : links) {
                linkObject.setVisible(true);
            }
        }
        else {
            List<LinkObject> links=getGraph().findTargetLinkObject(this);
            for (LinkObject linkObject : links) {
                linkObject.setVisible(false);
            }
        }
        super.setSelected(selected);
    }
    @Override
    public Point getLinkCenter(RendingObject relativeObject,boolean relativeObjectIsEnd) {
        Point fromPoint=relativeObject.getDrawingRect().center();
        Point point = getDrawingRect().checkIntersectionPointToCenter(fromPoint);
        if(point!=null)
        {
            return point;
        }
        return super.getLinkCenter(relativeObject, relativeObjectIsEnd);
    }
}
