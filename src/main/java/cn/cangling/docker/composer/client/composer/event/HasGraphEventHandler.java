package cn.cangling.docker.composer.client.composer.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasGraphEventHandler extends HasHandlers {
    HandlerRegistration addGraphEventHandler(GraphEventHandler handler);
}
