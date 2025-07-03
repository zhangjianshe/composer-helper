package cn.cangling.docker.composer.client.composer.editor;

import elemental2.dom.MouseEvent;

public interface IMouseAction {
    IMouseAction onDown( MouseEvent event);
    void onUp(MouseEvent event);
    void onMove( MouseEvent event);
}
