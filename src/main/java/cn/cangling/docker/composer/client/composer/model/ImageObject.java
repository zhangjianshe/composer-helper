package cn.cangling.docker.composer.client.composer.model;


import elemental2.dom.CanvasRenderingContext2D;
import elemental2.dom.HTMLImageElement;

public class  ImageObject<T> extends RendingObject<HTMLImageElement> {

    public ImageObject(HTMLImageElement icon) {
        super();
        setRefObject(icon);
    }
    public ImageObject() {
        super();
        setRefObject(null);
    }
    public ImageObject(HTMLImageElement icon, T refObject) {
        super();
        setRefObject(icon);
        setData(refObject);
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
        context2D.drawImage(getRefObject(), drawingRect.x, drawingRect.y, drawingRect.width, drawingRect.height);
        drawSelectedBorder(context2D,t,0);
    }
}
