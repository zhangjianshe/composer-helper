package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.*;
import elemental2.dom.DOMRect;
import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.MouseEvent;

public class DrawNetworkLinkAction implements IMouseAction {

    Point ptDown = new Point();
    Point ptCanvas = new Point();
    boolean isDown = false;
    LineObject lineObject = new LineObject();
    RendingObject source = null;
    RendingObject target = null;
    private YamlGraph graph;
    private HTMLCanvasElement canvas;

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
        assert hitTestResult.getHitTest() == HitTest.HT_START_NETWORK;
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
            if (hitTestResult.getData() instanceof NetworkObject) {
                target = hitTestResult.getData();
                if(target instanceof NetworkObject)
                {
                    NetworkObject networkObject = (NetworkObject) target;
                    //添加一个链接
                    RendingObject serviceObject = source.getParent();
                    if (serviceObject instanceof ServiceObject) {
                        ServiceObject serviceObject1 = (ServiceObject) serviceObject;
                       boolean b= serviceObject1.linkToNetwork(networkObject.getRefObject());
                       if(b) {
                           Link link = new Link(serviceObject1, target);
                           graph.addLink(link);
                       }
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
                if (hitTestResult.getData() instanceof NetworkObject) {
                    target = hitTestResult.getData();
                    target.setHilight(true);
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
