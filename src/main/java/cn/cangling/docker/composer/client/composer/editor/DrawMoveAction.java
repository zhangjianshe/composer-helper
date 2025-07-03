package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.DragBoxObject;
import cn.cangling.docker.composer.client.composer.model.Point;
import cn.cangling.docker.composer.client.composer.model.RendingObject;
import cn.cangling.docker.composer.client.composer.model.YamlGraph;
import com.google.gwt.dom.client.Style;
import elemental2.dom.DOMRect;
import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.MouseEvent;

import java.util.List;

/**
 * 移动选中的目标
 */
public class DrawMoveAction implements IMouseAction{
    private YamlGraph graph;
    private HTMLCanvasElement canvas;
    boolean isDown=false;
    Point ptDown=new Point();
    public void setData(YamlGraph graph, HTMLCanvasElement canvas) {
        this.graph = graph;
        this.canvas = canvas;
    }
    @Override
    public IMouseAction onDown(MouseEvent event) {
        ptDown.set(event.x, event.y);
        isDown=true;
        canvas.style.cursor= "move";
        return null;
    }

    @Override
    public void onUp(MouseEvent event) {
        canvas.style.cursor="default";
        isDown=false;
    }

    @Override
    public void onMove(MouseEvent event) {
        if(isDown) {
            //拉框
            double dx=event.x - ptDown.x;
            double dy=event.y - ptDown.y;
            graph.offsetSelected(dx,dy);
            ptDown.set(event.x, event.y);
        }
    }
}
