package com.zagvladimir.views.helloworld;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.zagvladimir.views.MainLayout;

@PageTitle("Hello World")
@Route(value = "hello/:tailID?", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout implements BeforeEnterObserver{

    private String tailId;

    public HelloWorldView() {
        setSpacing(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        tailId = beforeEnterEvent.getRouteParameters().get("tailID").
                orElse("22");

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
        add(new H2(tailId));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗" + tailId));
    }
}
