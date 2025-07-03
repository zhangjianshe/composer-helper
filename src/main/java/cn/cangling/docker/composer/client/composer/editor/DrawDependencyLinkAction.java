package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.*;
import elemental2.dom.DOMRect;
import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.MouseEvent;

/**
 * 绘制依赖链接
 */
public class DrawDependencyLinkAction implements IMouseAction {

    Point ptDown = new Point();
    Point ptCanvas = new Point();
    boolean isDown = false;
    LineObject lineObject ;
    RendingObject source = null;
    RendingObject target = null;
    private YamlGraph graph;
    private HTMLCanvasElement canvas;
    static final double[] DASH={3,3};

    public DrawDependencyLinkAction()
    {
        lineObject=new LineObject();
        lineObject.setLineWidth(2);
        lineObject.setEndArrow(10,5);
        lineObject.setStrokeColor("blue");
        lineObject.setDash(DASH);
    }
    public void setData(YamlGraph graph, HTMLCanvasElement canvas) {
        this.graph = graph;
        this.canvas = canvas;
    }

    @Override
    public IMouseAction onDown(MouseEvent event) {
        ptDown.set(event.x, event.y);
        isDown = true;
        DOMRect boundingClientRect = canvas.getBoundingClientRect();
        ptCanvas.set(event.clientX - boundingClientRect.x, event.clientY - boundingClientRect.y);
        HitTestResult hitTestResult = graph.hitTest(ptCanvas);
        assert hitTestResult.getHitTest() == HitTest.HT_START_DEPENDENCY;
        source = hitTestResult.getData();
        source.setHilight(true);

        lineObject.setSource(ptCanvas.x, ptCanvas.y);
        lineObject.setTarget(ptCanvas.x, ptCanvas.y);

        graph.addActionObject(lineObject);
        return null;
    }

    @Override
    public void onUp(MouseEvent event) {
        isDown = false;
        source.setHilight(false);

        DOMRect boundingClientRect = canvas.getBoundingClientRect();
        ptCanvas.set(event.clientX - boundingClientRect.x, event.clientY - boundingClientRect.y);
        lineObject.setTarget(ptCanvas.x, ptCanvas.y);
        HitTestResult hitTestResult = graph.hitTest(ptCanvas);
        if (hitTestResult.getHitTest() == HitTest.HT_OBJECT) {
            if (hitTestResult.getData() instanceof ServiceObject) {
                target = hitTestResult.getData();
                ServiceObject targetServiceObject = (ServiceObject) target;
                RendingObject serviceObject = source.getParent();
                if (serviceObject instanceof ServiceObject) {
                    ServiceObject sourceServiceObject = (ServiceObject) serviceObject;
                    //添加依赖链接
                   boolean b= sourceServiceObject.linkToDependency(targetServiceObject.getRefObject());
                   if(b) {
                       Link link = new Link(sourceServiceObject, targetServiceObject);
                       link.setLinkKind(LinkKind.LINK_KIND_DEPENDENCY);
                       graph.addLink(link);
                   }
                }
            }
        }

        if (target != null) {
            target.setHilight(false);
            target = null;
        }
        graph.removeActionObject(lineObject);
    }

    @Override
    public void onMove(MouseEvent event) {
        if (isDown) {
            DOMRect boundingClientRect = canvas.getBoundingClientRect();
            ptCanvas.set(event.clientX - boundingClientRect.x, event.clientY - boundingClientRect.y);
            lineObject.setTarget(ptCanvas.x, ptCanvas.y);
            HitTestResult hitTestResult = graph.hitTest(ptCanvas);
            if (hitTestResult.getHitTest() == HitTest.HT_OBJECT) {
                if (hitTestResult.getData() instanceof ServiceObject) {
                    if(source.getParent().equals(hitTestResult.getData())) {
                        //目标和源一样
                    }
                    else {
                        target = hitTestResult.getData();
                        target.setHilight(true);
                    }
                }
            } else {
                if (target != null) {
                    target.setHilight(false);
                    target = null;
                }
            }
        }
    }
}
