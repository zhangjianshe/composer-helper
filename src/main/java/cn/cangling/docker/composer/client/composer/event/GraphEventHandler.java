package cn.cangling.docker.composer.client.composer.event;

import com.google.gwt.event.shared.EventHandler;

public interface GraphEventHandler extends EventHandler {
    void onGraph(GraphEvent event);
}
