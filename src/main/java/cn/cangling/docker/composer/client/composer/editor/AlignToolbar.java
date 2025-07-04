package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.core.client.GWT;
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

public class AlignToolbar extends Composite implements HasValueChangeHandlers<ToolbarCommands> {
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ToolbarCommands> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    interface AlignToolbarUiBinder extends UiBinder<HTMLPanel, AlignToolbar> {
    }

    private static AlignToolbarUiBinder ourUiBinder = GWT.create(AlignToolbarUiBinder.class);
    @UiField
    HTML btnAlignLeft;
    @UiField
    HTML btnAlignCenter;
    @UiField
    HTML btnAlignRight;
    @UiField
    HTML btnAlignTop;
    @UiField
    HTML btnAlignMiddle;
    @UiField
    HTML btnAlignBottom;

    public AlignToolbar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        btnAlignLeft.setHTML(ComposerResource.INSTANCE.align_left().getText());
        btnAlignMiddle.setHTML(ComposerResource.INSTANCE.align_middle().getText());
        btnAlignRight.setHTML(ComposerResource.INSTANCE.align_right().getText());
        btnAlignTop.setHTML(ComposerResource.INSTANCE.align_top().getText());
        btnAlignBottom.setHTML(ComposerResource.INSTANCE.align_bottom().getText());
        btnAlignCenter.setHTML(ComposerResource.INSTANCE.align_center().getText());
    }

    @UiHandler("btnAlignLeft")
    public void btnAlignLeftClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_LEFT);
    }

    @UiHandler("btnAlignCenter")
    public void btnAlignCenterClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_CENTER);
    }

    @UiHandler("btnAlignRight")
    public void btnAlignRightClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_RIGHT);
    }

    @UiHandler("btnAlignTop")
    public void btnAlignTopClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_TOP);
    }

    @UiHandler("btnAlignMiddle")
    public void btnAlignMiddleClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_MIDDLE);
    }

    @UiHandler("btnAlignBottom")
    public void btnAlignBottomClick(ClickEvent event) {
        ValueChangeEvent.fire(this,ToolbarCommands.CMD_ALIGN_BOTTOM);
    }
}