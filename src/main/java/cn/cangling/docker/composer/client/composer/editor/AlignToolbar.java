package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
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
    SvgButton btnAlignLeft;
    @UiField
    SvgButton btnAlignCenter;
    @UiField
    SvgButton btnAlignRight;
    @UiField
    SvgButton btnAlignTop;
    @UiField
    SvgButton btnAlignMiddle;
    @UiField
    SvgButton btnAlignBottom;

    public AlignToolbar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        btnAlignLeft.setSVG(ComposerResource.INSTANCE.align_left().getText());
        btnAlignMiddle.setSVG(ComposerResource.INSTANCE.align_middle().getText());
        btnAlignRight.setSVG(ComposerResource.INSTANCE.align_right().getText());
        btnAlignTop.setSVG(ComposerResource.INSTANCE.align_top().getText());
        btnAlignBottom.setSVG(ComposerResource.INSTANCE.align_bottom().getText());
        btnAlignCenter.setSVG(ComposerResource.INSTANCE.align_center().getText());
    }

    @UiHandler("btnAlignLeft")
    public void btnAlignLeftClick(ClickEvent event) {
        if(btnAlignLeft.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_LEFT);
        }
    }

    @UiHandler("btnAlignCenter")
    public void btnAlignCenterClick(ClickEvent event) {
        if(btnAlignCenter.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_CENTER);
        }
    }

    @UiHandler("btnAlignRight")
    public void btnAlignRightClick(ClickEvent event) {
        if(btnAlignRight.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_RIGHT);
        }
    }

    @UiHandler("btnAlignTop")
    public void btnAlignTopClick(ClickEvent event) {
        if (btnAlignTop.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_TOP);
        }
    }

    @UiHandler("btnAlignMiddle")
    public void btnAlignMiddleClick(ClickEvent event) {
        if(btnAlignMiddle.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_MIDDLE);
        }
    }

    @UiHandler("btnAlignBottom")
    public void btnAlignBottomClick(ClickEvent event) {
        if(btnAlignBottom.enabled) {
            ValueChangeEvent.fire(this, ToolbarCommands.CMD_ALIGN_BOTTOM);
        }
    }

    public void enableAll(boolean enabled) {
        btnAlignLeft.setEnabled(enabled);
        btnAlignBottom.setEnabled(enabled);
        btnAlignCenter.setEnabled(enabled);
        btnAlignMiddle.setEnabled(enabled);
        btnAlignRight.setEnabled(enabled);
        btnAlignTop.setEnabled(enabled);
    }
}