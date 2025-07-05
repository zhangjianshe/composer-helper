package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class OperationToolbar extends Composite
        implements HasValueChangeHandlers<ToolbarCommands> {


    private static final OperationToolbarUiBinder ourUiBinder = GWT.create(OperationToolbarUiBinder.class);
    @UiField
    SvgButton btnDelete;

    public OperationToolbar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        btnDelete.setSVG(ComposerResource.INSTANCE.delete().getText());
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ToolbarCommands> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @UiHandler("btnDelete")
    public void btnDeleteClick(ClickEvent event) {
        if(btnDelete.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_DELETE);
        }
    }
    public void enableAll(boolean enabled) {
        btnDelete.setEnabled(enabled);
    }

    interface OperationToolbarUiBinder extends UiBinder<HTMLPanel, OperationToolbar> {
    }
}