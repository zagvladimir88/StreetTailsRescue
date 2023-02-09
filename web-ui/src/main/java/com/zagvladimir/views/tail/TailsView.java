package com.zagvladimir.views.tail;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.zagvladimir.views.MainLayout;

@PageTitle("Хвостатые")
@Route(value = "tails/:tailID?", layout = MainLayout.class)
public class TailsView extends VerticalLayout implements BeforeEnterObserver{

    private String tailId;

    public TailsView() {
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
        var horizontalLayout = new HorizontalLayout();
        Image img = new Image("images/empty-plant.png", "placeholder plant");
        Image img2 = new Image("images/empty-plant.png", "placeholder plant");
        Image img3 = new Image("images/empty-plant.png", "placeholder plant");
        horizontalLayout.add(img2,img3);
        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroller.setContent(horizontalLayout);
        img.setWidth("200px");
        add(scroller);
        add(new H2(tailId));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗" + tailId));

    }
}
