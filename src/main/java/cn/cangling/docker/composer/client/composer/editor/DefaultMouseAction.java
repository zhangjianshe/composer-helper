package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.*;
import elemental2.dom.DOMRect;
import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.MouseEvent;

/**
 * 缺省的鼠标行为 点击选择
 */
public class DefaultMouseAction implements IMouseAction{


    DrawVolumeLinkAction drawVolumeLinkAction;
    DrawNetworkLinkAction drawNetworkLinkAction;
    DrawDependencyLinkAction drawDependencyLinkAction;
    DrawDragBoxAction drawDragBoxAction;
    DrawMoveAction drawMoveAction;
    public DefaultMouseAction()
    {
        drawVolumeLinkAction=new DrawVolumeLinkAction();
        drawNetworkLinkAction=new DrawNetworkLinkAction();
        drawDependencyLinkAction=new DrawDependencyLinkAction();
        drawDragBoxAction=new DrawDragBoxAction();
        drawMoveAction=new DrawMoveAction();
    }
    Point ptDown = new Point();
    Point ptCanvas = new Point();
    boolean isDown = false;
    YamlGraph graph = null;
    HTMLCanvasElement canvas = null;
    public void setData(YamlGraph graph, HTMLCanvasElement canvasElement) {
        this.graph = graph;
        this.canvas = canvasElement;
    }
    @Override
    public IMouseAction onDown(MouseEvent event) {
        if(graph==null || canvas==null)
        {
            return null;
        }
        DOMRect clientRect = canvas.getBoundingClientRect();
        ptDown.set(event.x, event.y);
        double canvasX=event.clientX-clientRect.left;
        double canvasY =event.clientY-clientRect.top;
        ptCanvas.set((int)canvasX, (int)canvasY);

        if(graph.getSelectObjectList().isEmpty()) {
            HitTestResult hitTestResult = graph.hitTest(ptCanvas);
            switch (hitTestResult.getHitTest()) {
                case HT_START_VOLUME:
                    drawVolumeLinkAction.setData(graph, canvas);
                    return drawVolumeLinkAction;
                case HT_START_NETWORK:
                    drawNetworkLinkAction.setData(graph, canvas);
                    return drawNetworkLinkAction;
                case HT_START_DEPENDENCY:
                    drawDependencyLinkAction.setData(graph, canvas);
                    return drawDependencyLinkAction;
                case HT_OBJECT:
                    isDown = true;
                    graph.clearSelected();
                    graph.appendSelectObject(hitTestResult.getData(), true);
                    drawMoveAction.setData(graph, canvas);
                    return drawMoveAction;
                case HT_NONE:
                default:
                    drawDragBoxAction.setData(graph, canvas);
                    return drawDragBoxAction;
            }
        }
        else {
            //之前有选中的操作 只有清空或者移动的操作
            if (graph.pointInSelection(ptCanvas.x, ptCanvas.y)) {
                if(event.ctrlKey)
                {
                    HitTestResult hitTestResult = graph.hitTest(ptCanvas);
                    switch (hitTestResult.getHitTest()) {
                        case HT_OBJECT:
                            // ctrl + click (has selected item) -> remove it from the selected list
                            graph.removeSelectObject(hitTestResult.getData(), true);
                            return null;
                        default:
                            drawMoveAction.setData(graph, canvas);
                            return drawMoveAction;
                    }
                }
                else {
                    drawMoveAction.setData(graph, canvas);
                    return drawMoveAction;
                }
            }
            else {
                //清空选择 进行下一次拉框选择
                HitTestResult hitTestResult = graph.hitTest(ptCanvas);
                switch (hitTestResult.getHitTest()) {
                    case HT_START_VOLUME:
                        graph.clearSelected();
                        drawVolumeLinkAction.setData(graph, canvas);
                        return drawVolumeLinkAction;
                    case HT_START_NETWORK:
                        graph.clearSelected();
                        drawNetworkLinkAction.setData(graph, canvas);
                        return drawNetworkLinkAction;
                    case HT_START_DEPENDENCY:
                        graph.clearSelected();
                        drawDependencyLinkAction.setData(graph, canvas);
                        return drawDependencyLinkAction;
                    case HT_OBJECT:
                        if(event.ctrlKey)
                        {
                            // Ctrl+Click  append selected item to the selected list
                            graph.appendSelectObject(hitTestResult.getData(), true);
                            return null;
                        }
                        else {
                            graph.clearSelected();
                            graph.appendSelectObject(hitTestResult.getData(), true);
                            drawMoveAction.setData(graph, canvas);
                            return drawMoveAction;
                        }
                    default:
                        graph.clearSelected();
                        drawDragBoxAction.setData(graph, canvas);
                        return drawDragBoxAction;
                }

            }

        }
    }

    @Override
    public void onMove( MouseEvent event) {
        if(graph==null || canvas==null)
        {
            return ;
        }
    }

    @Override
    public void onUp(MouseEvent event) {
        if(graph==null || canvas==null)
        {
            return ;
        }
        isDown = false;
    }
}
