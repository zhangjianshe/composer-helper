package cn.cangling.docker.composer.client.composer.event;

import cn.cangling.docker.composer.client.composer.model.YamlGraph;
import com.google.gwt.event.shared.GwtEvent;

public class GraphEvent extends GwtEvent<GraphEventHandler> {
    Object data;
    EventType eventType;
    public static Type<GraphEventHandler> TYPE = new Type<GraphEventHandler>();
    public void setData(Object data) {
        this.data = data;
    }

    public static GraphEvent selectChangeEvent(YamlGraph graph)
    {
        return new GraphEvent(EventType.ET_SELECT_CHANGED, graph);
    }
    public <T> T getData()
    {
        return (T)data;
    }
    public EventType getEventType() {
        return eventType;
    }
    public GraphEvent()
    {
        this.eventType=EventType.ET_NONE;
        this.data=null;
    }

    public GraphEvent(EventType eventType, Object data) {
        this.data = data;
        this.eventType=eventType;
    }
    public Type<GraphEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(GraphEventHandler handler) {
        handler.onGraph(this);
    }
}
