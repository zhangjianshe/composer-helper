package cn.cangling.docker.composer.client.composer;

import cn.cangling.docker.composer.client.composer.editor.AlignToolbar;
import cn.cangling.docker.composer.client.composer.editor.ComposerEditor;
import cn.cangling.docker.composer.client.composer.editor.FileToolbar;
import cn.cangling.docker.composer.client.composer.editor.ToolbarCommands;
import cn.cangling.docker.composer.client.composer.event.GraphEvent;
import cn.cangling.docker.composer.client.composer.event.GraphEventHandler;
import cn.cangling.docker.composer.client.composer.event.HasGraphEventHandler;
import cn.cangling.docker.composer.client.composer.model.YamlGraph;
import cn.cangling.docker.composer.client.composer.template.ObjectTemplates;
import cn.cangling.docker.composer.client.composer.template.TemplateItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ComposerFrame extends Composite implements RequiresResize, HasGraphEventHandler {
    private static final ComposerFrameUiBinder ourUiBinder = GWT.create(ComposerFrameUiBinder.class);
    @UiField
    LayoutPanel root;
    @UiField
    HTMLPanel list;
    @UiField
    ComposerEditor editor;
    @UiField
    AlignToolbar alignToolbar;
    @UiField
    FileToolbar fileToolbar;

    public ComposerFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        fileToolbar.addValueChangeHandler(event -> processHandler(event.getValue()));
        alignToolbar.addValueChangeHandler(event -> processHandler(event.getValue()));
        alignToolbar.enableAll(false);
    }

    private void processHandler(ToolbarCommands value) {
        switch (value) {
            case CMD_OPEN: {
                Storage storage = Storage.getLocalStorageIfSupported();
                String tempGraph = storage.getItem("temp_graph");
                editor.getYamlGraph().loadFromJson(tempGraph);
                fireEvent(GraphEvent.messageEvent("load from storage"));
                break;
            }
            case CMD_SAVE: {
                String data = editor.getYamlGraph().exportToJson();
                Storage storage = Storage.getLocalStorageIfSupported();
                storage.setItem("temp_graph", data);
                fireEvent(GraphEvent.messageEvent("graph saved"));
                break;
            }
            case CMD_ALIGN_TOP:
            case CMD_ALIGN_LEFT:
            case CMD_ALIGN_RIGHT:
            case CMD_ALIGN_BOTTOM:
            case CMD_ALIGN_CENTER:
            case CMD_ALIGN_MIDDLE:
                editor.doAlignCommand(value);
            default:
        }
    }

    @Override
    public void onResize() {
        root.onResize();
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        initTemplate();
    }

    private void initTemplate() {
        list.clear();
        ObjectTemplates objectTemplates = ObjectTemplates.get();
        objectTemplates.each(templateItem -> {
            TemplateItem templateItem1 = new TemplateItem();
            templateItem1.setData(templateItem);
            list.add(templateItem1);
        });
    }

    @UiHandler("editor")
    public void editorGraph(GraphEvent event) {
        switch (event.getEventType()) {
            case ET_SELECT_CHANGED:
                YamlGraph graph = event.getData();
                alignToolbar.enableAll(!graph.getSelectObjectList().isEmpty());
                break;
            case ET_MESSAGE:
                fireEvent(event);
                break;
            case ET_NONE:
            default:
        }
    }

    @Override
    public HandlerRegistration addGraphEventHandler(GraphEventHandler handler) {
        return addHandler(handler,GraphEvent.TYPE);
    }

    interface ComposerFrameUiBinder extends UiBinder<LayoutPanel, ComposerFrame> {
    }
}