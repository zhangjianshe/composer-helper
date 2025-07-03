package cn.cangling.docker.composer.client.composer.model;

import elemental2.dom.BaseRenderingContext2D;
import elemental2.dom.CanvasRenderingContext2D;

import java.util.Random;

public class RendingObject<T> {
    private static final Random RANDOM = new Random();
    private final Rect rect;
    Rect drawingRect = new Rect();
    Rect rectBorder = new Rect();
    boolean selected = false;
    private final String id;
    private T refObject;
    private YamlGraph graph;
    private RendingObject parent;
    private boolean visible = true;
    private boolean hilight=false;

    public void setHilight(boolean hilight) {
        this.hilight = hilight;
    }
    public boolean isHilight() {
        return hilight;
    }

    public RendingObject() {
        id = ("" + Math.abs(RANDOM.nextDouble()) * 10000000).substring(0, 7);
        rect = new Rect();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public RendingObject getParent() {
        return parent;
    }

    public void setParent(RendingObject parent) {
        this.parent = parent;
    }

    public YamlGraph getGraph() {
        return graph;
    }

    public void setGraph(YamlGraph graph) {
        this.graph = graph;
    }

    public T getRefObject() {
        return refObject;
    }

    public void setRefObject(T refObject) {
        this.refObject = refObject;
    }

    public void rect(double x, double y, double width, double height) {
        rect.set(x, y);
        rect.setWidth(width);
        rect.setHeight(height);
    }

    public void draw(CanvasRenderingContext2D context2D, double t) {
    }

    public Rect getRect() {
        return rect;
    }

    public native void roundRect(CanvasRenderingContext2D ctx, double x, double y, double width, double height, double radius)/*-{
        if (ctx.roundRect) {
            ctx.roundRect(x, y, width, height, [radius]);
        } else {
            ctx.rect(x, y, width, height);
        }
    }-*/;

    public Rect getDrawingRect() {
        if (parent != null) {
            drawingRect.copyFrom(rect);
            Rect parentDrawingRect = parent.getDrawingRect();
            drawingRect.offset(parentDrawingRect.x, parentDrawingRect.y);
        } else {
            drawingRect.copyFrom(rect);
        }
        rectBorder.copyFrom(drawingRect);
        rectBorder.expand(5, 5);
        return drawingRect;
    }

    /**
     * 获取 (与relativeObject)  相关连接的中心点
     *   [relative]   ------>  [this.getLinkCenetr()]
     * @param relativeObject
     * @param relativeObjectIsEnd 表明这个是这个的连接的原还是目标
     *                                true, relativeObject is end of the link
     *                                false, relativeObject is start of the link
     * @return
     */
    public Point getLinkCenter(RendingObject relativeObject,boolean relativeObjectIsEnd) {
        return getDrawingRect().center();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Rect getBorderRect() {
        return rectBorder;
    }

    public void drawSelectedBorder(CanvasRenderingContext2D context2D, double t,double radius) {
        if(hilight)
        {
            context2D.save();
            Rect border = getBorderRect();
            context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of("red");
            context2D.setLineWidth(1);
            context2D.setLineDash(new double[]{5, 5});
            context2D.setLineJoin("round");
            roundRect(context2D,border.x, border.y, border.width, border.height,radius);
            context2D.stroke();
            context2D.restore();
        }
        else if (selected) {
            context2D.save();
            Rect border = getBorderRect();
            context2D.strokeStyle = BaseRenderingContext2D.StrokeStyleUnionType.of("blue");
            context2D.setLineWidth(1);
            context2D.setLineDash(new double[]{5, 5});
            context2D.setLineJoin("round");
            roundRect(context2D,border.x, border.y, border.width, border.height,radius);
            context2D.stroke();
            context2D.restore();
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RendingObject<?> that = (RendingObject<?>) o;
        return id.equals(that.id);
    }

    public void layout() {

    }

    public HitTestResult hitTest(double canvasX,double canvasY){
        if(getDrawingRect().contains((int)canvasX,(int)canvasY)){
            return HitTestResult.create(HitTest.HT_OBJECT,this);
        }
        return HitTestResult.create(HitTest.HT_NONE,null);
    }

    public boolean intersect(Rect rect) {
        return  getDrawingRect().intersect(rect);
    }
}
