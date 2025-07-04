package cn.cangling.docker.composer.client.composer.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import elemental2.dom.CSSProperties;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

public class SvgButton extends Composite implements HasClickHandlers {
    private static final SvgButtonUiBinder ourUiBinder = GWT.create(SvgButtonUiBinder.class);
    @UiField
    HTML root;
    boolean enabled = true;

    public SvgButton() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            root.getElement().removeAttribute("disabled");
        } else {
            root.getElement().setAttribute("disabled", "true");
        }
    }

    public void setSVG(String svg) {
        root.setHTML(svg);
    }

    public void setButtonSize(int size) {
        Element rootElement = Js.uncheckedCast(root.getElement());
        HTMLElement element = Js.uncheckedCast(rootElement.querySelector(":scope svg"));
        if (element != null) {
            element.setAttribute("width", size + "px");
            element.setAttribute("height", size + "px");
            element.style.width = CSSProperties.WidthUnionType.of(size + "px");
            element.style.height = CSSProperties.HeightUnionType.of(size + "px");
        } else {
            DomGlobal.console.log("Element not found");
        }
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    interface SvgButtonUiBinder extends UiBinder<HTML, SvgButton> {
    }
}