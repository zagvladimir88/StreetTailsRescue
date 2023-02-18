package com.zagvladimir.components.appnav;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.StateTree;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.VaadinService;
import java.util.Optional;

@JsModule("@vaadin-component-factory/vcf-nav")
@Tag("vcf-nav-item")
public class AppNavItem extends Component {

    public AppNavItem(String label) {
        setLabel(label);
    }

    public AppNavItem(String label, String path) {
        setPath(path);
        setLabel(label);
    }

    public AppNavItem(String label, Class<? extends Component> view) {
        setPath(view);
        setLabel(label);
    }

    public AppNavItem(String label, String path, Component icon) {
        setPath(path);
        setLabel(label);
        setIcon(icon);
    }

    public AppNavItem(String label, Class<? extends Component> view, Component icon) {
        setPath(view);
        setLabel(label);
        setIcon(icon);
    }

    public AppNavItem(String label, String path, String iconClass) {
        setPath(path);
        setLabel(label);

        setIconClass(iconClass);
    }

    public AppNavItem(String label, Class<? extends Component> view, String iconClass) {
        setPath(view);
        setLabel(label);

        setIconClass(iconClass);
    }

    public AppNavItem addItem(AppNavItem... appNavItems) {
        for (AppNavItem appNavItem : appNavItems) {
            appNavItem.getElement().setAttribute("slot", "children");
            getElement().appendChild(appNavItem.getElement());
        }

        return this;
    }

    public AppNavItem removeItem(AppNavItem appNavItem) {
        Optional<Component> parent = appNavItem.getParent();
        if (parent.isPresent() && parent.get() == this) {
            getElement().removeChild(appNavItem.getElement());
        }

        return this;
    }

    public AppNavItem removeAllItems() {
        getElement().removeAllChildren();
        return this;
    }

    public String getLabel() {
        return getExistingLabelElement().map(e -> e.getText()).orElse(null);
    }

    public AppNavItem setLabel(String label) {
        getLabelElement().setText(label);
        return this;
    }

    private Optional<Element> getExistingLabelElement() {
        return getElement().getChildren().filter(child -> !child.hasAttribute("slot")).findFirst();
    }

    private Element getLabelElement() {
        return getExistingLabelElement().orElseGet(() -> {
            Element element = Element.createText("");
            getElement().appendChild(element);
            return element;
        });
    }

    public AppNavItem setPath(String path) {
        getElement().setAttribute("path", path);
        return this;
    }

    public AppNavItem setPath(Class<? extends Component> view) {
        String url = RouteConfiguration.forRegistry(getRouter().getRegistry()).getUrl(view);
        setPath(url);
        return this;
    }

    private Router getRouter() {
        Router router = null;
        if (getElement().getNode().isAttached()) {
            StateTree tree = (StateTree) getElement().getNode().getOwner();
            router = tree.getUI().getInternals().getRouter();
        }
        if (router == null) {
            router = VaadinService.getCurrent().getRouter();
        }
        if (router == null) {
            throw new IllegalStateException("Implicit router instance is not available. "
                    + "Use overloaded method with explicit router parameter.");
        }
        return router;
    }

    public String getPath() {
        return getElement().getAttribute("path");
    }

    private int getIconElementIndex() {
        for (int i = 0; i < getElement().getChildCount(); i++) {
            if ("prefix".equals(getElement().getChild(i).getAttribute("slot"))) {
                return i;
            }
        }
        return -1;
    }

    public AppNavItem setIcon(Component icon) {
        icon.getElement().setAttribute("slot", "prefix");
        int iconElementIndex = getIconElementIndex();
        if (iconElementIndex != -1) {
            getElement().setChild(iconElementIndex, icon.getElement());
        } else {
            getElement().appendChild(icon.getElement());
        }
        return this;
    }

    public AppNavItem setIconClass(String iconClass) {
        Span icon = new Span();
        icon.setClassName(iconClass);
        setIcon(icon);
        return this;
    }

    public AppNavItem setExpanded(boolean value) {
        if (value) {
            getElement().setAttribute("expanded", "");
        } else {
            getElement().removeAttribute("expanded");
        }
        return this;
    }

}
