package cn.cangling.docker.composer.client.composer.model;


import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.HTMLImageElement;

public class LabelObject<T> extends RendingObject<String> {

    public LabelObject(String text) {
        super();
        setRefObject(text);
    }
    public LabelObject() {
        super();
        setRefObject("");
    }
    public LabelObject(String icon, T data) {
        super();
        setRefObject(icon);
        setData(data);
    }
    T data;
    public T getData()
    {
        return data;
    }
    public void setData(T data)
    {
        this.data=data;
    }
    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {
        if(getRefObject()==null)
            return;
        Rect drawingRect=getDrawingRect();
        context2D.save();
        if(isSelected())
        {
            context2D.font="bold 16px 'Microsoft YaHei'";
        }
        else {
            context2D.font="16px 'Microsoft YaHei'";
        }
        context2D.fillStyle= BaseRenderingContext2D.FillStyleUnionType.of("black");
        context2D.fillText(getRefObject(), drawingRect.x, drawingRect.y+drawingRect.height,drawingRect.width);
        context2D.restore();
    }
}
