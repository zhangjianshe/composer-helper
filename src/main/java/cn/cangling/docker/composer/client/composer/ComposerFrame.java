package cn.cangling.docker.composer.client.composer;

import cn.cangling.docker.composer.client.composer.editor.ComposerEditor;
import cn.cangling.docker.composer.client.composer.template.ObjectTemplates;
import cn.cangling.docker.composer.client.composer.template.TemplateItem;
import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ComposerFrame extends Composite implements RequiresResize {
    private static final ComposerFrameUiBinder ourUiBinder = GWT.create(ComposerFrameUiBinder.class);
    @UiField
    DockLayoutPanel root;
    @UiField
    HTMLPanel list;
    @UiField
    Image btnOpen;
    @UiField
    Image btnSave;
    @UiField
    ComposerEditor editor;
    public ComposerFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        btnOpen.setResource(ComposerResource.INSTANCE.open());
        btnSave.setResource(ComposerResource.INSTANCE.save());
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

    @UiHandler("btnSave")
    public void btnSaveClick(ClickEvent event) {
        String data = editor.getYamlGraph().exportToJson();
        Storage storage = Storage.getLocalStorageIfSupported();
        storage.setItem("temp_graph", data);
        Window.setTitle("graph saved");
    }

    @UiHandler("btnOpen")
    public void btnOpenClick(ClickEvent event) {
        Storage storage = Storage.getLocalStorageIfSupported();
        String tempGraph = storage.getItem("temp_graph");
        editor.getYamlGraph().loadFromJson(tempGraph);
    }

    interface ComposerFrameUiBinder extends UiBinder<DockLayoutPanel, ComposerFrame> {
    }
}