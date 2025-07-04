package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class FileToolbar extends Composite implements HasValueChangeHandlers<ToolbarCommands> {


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ToolbarCommands> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    interface FileToolbarUiBinder extends UiBinder<HTMLPanel, FileToolbar> {
    }

    private static FileToolbarUiBinder ourUiBinder = GWT.create(FileToolbarUiBinder.class);
    @UiField
    HTML btnOpen;
    @UiField
    HTML btnSave;

    public FileToolbar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        btnOpen.setHTML(ComposerResource.INSTANCE.open_svg().getText());
        btnSave.setHTML(ComposerResource.INSTANCE.save_svg().getText());
    }

    @UiHandler("btnOpen")
    public void btnOpenClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_OPEN);
    }

    @UiHandler("btnSave")
    public void btnSaveClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_SAVE);
    }
}