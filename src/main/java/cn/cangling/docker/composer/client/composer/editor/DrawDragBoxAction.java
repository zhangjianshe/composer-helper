package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.DragBoxObject;
import cn.cangling.docker.composer.client.composer.model.RendingObject;
import cn.cangling.docker.composer.client.composer.model.YamlGraph;
import elemental2.dom.DOMRect;
import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.MouseEvent;

import java.util.List;


public class DrawDragBoxAction implements IMouseAction{
    private YamlGraph graph;
    private HTMLCanvasElement canvas;
    boolean isDown=false;
    DragBoxObject dragBoxObject;
    public void setData(YamlGraph graph, HTMLCanvasElement canvas) {
        this.graph = graph;
        this.canvas = canvas;
    }
    @Override
    public IMouseAction onDown(MouseEvent event) {

        DOMRect clientRect = canvas.getBoundingClientRect();
        double canvasX = event.clientX - clientRect.left;
        double canvasY = event.clientY - clientRect.top;
        dragBoxObject =graph.enableDragBox(true);
        dragBoxObject.setStart(canvasX, canvasY);
        dragBoxObject.setEnd(canvasX, canvasY);
        isDown=true;
        return null;
    }

    @Override
    public void onUp(MouseEvent event) {
        DOMRect clientRect = canvas.getBoundingClientRect();
        double canvasX = event.clientX - clientRect.left;
        double canvasY = event.clientY - clientRect.top;
        if (dragBoxObject != null) {
            dragBoxObject.setEnd(canvasX, canvasY);
            //高亮框选的内容
            List<RendingObject> highlight = graph.highlight(dragBoxObject.getRect());
            graph.appendSelectObjects(true, highlight);
            graph.clearHighlight();
        }
        graph.enableDragBox(false);
        isDown=true;
    }

    @Override
    public void onMove(MouseEvent event) {
        if(isDown) {
            //拉框
            DOMRect clientRect = canvas.getBoundingClientRect();
            double canvasX = event.clientX - clientRect.left;
            double canvasY = event.clientY - clientRect.top;
            if (dragBoxObject != null) {
                dragBoxObject.setEnd(canvasX, canvasY);
                //高亮框选的内容
                graph.highlight(dragBoxObject.getRect());
            }

        }
    }
}
