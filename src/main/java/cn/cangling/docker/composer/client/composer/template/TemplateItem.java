package cn.cangling.docker.composer.client.composer.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import elemental2.dom.DragEvent;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

public class TemplateItem extends Composite {
    private static final TemplateItemUiBinder ourUiBinder = GWT.create(TemplateItemUiBinder.class);
    ObjectTemplate data;
    @UiField
    Label lbName;
    @UiField
    Image image;
    public TemplateItem() {
        initWidget(ourUiBinder.createAndBindUi(this));
        getElement().setAttribute("draggable", "true");
        installDragEvent();
    }

    public void setData(ObjectTemplate data) {
        lbName.setText(data.name);
        image.setUrl(data.image.src);

        this.data = data;
    }

    private void installDragEvent() {
        final HTMLElement element = Js.uncheckedCast(getElement());
        element.addEventListener("dragstart", event -> {
                    DragEvent dragEvent = Js.uncheckedCast(event);
                    dragEvent.dataTransfer.setData(ObjectTemplates.DATA_TYPE, data.name);
                    dragEvent.dataTransfer.setDragImage(element, element.offsetWidth/2, element.offsetHeight/2);
                }
                , false);
    }

    interface TemplateItemUiBinder extends UiBinder<HTMLPanel, TemplateItem> {
    }
}