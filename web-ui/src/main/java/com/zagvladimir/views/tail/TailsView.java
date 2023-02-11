package com.zagvladimir.views.tail;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zagvladimir.service.image.ImageService;
import com.zagvladimir.service.tail.TailServiceImpl;
import com.zagvladimir.views.MainLayout;

import java.net.URL;

@PageTitle("Хвостатые")
@Route(value = "tails/:tailID?", layout = MainLayout.class)
@AnonymousAllowed
public class TailsView extends VerticalLayout implements BeforeEnterObserver{

    private String tailId;
    private final TailServiceImpl tailService;
    private final ImageService imageService;

    public TailsView(TailServiceImpl tailService, ImageService imageService) {
        this.tailService = tailService;
        this.imageService = imageService;
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

        var imageUrls = imageService.getUrls(Integer.valueOf(tailId));
        var tailById = tailService.findById(Integer.valueOf(tailId));

        var horizontalLayout = new HorizontalLayout();
        for (URL url : imageUrls) {
            var tailImage = new Image(url.toString(), "tail");
            tailImage.setWidth(50f,Unit.PERCENTAGE);
            tailImage.addClickListener(e -> UI.getCurrent().getPage().open(url.toString()));
            horizontalLayout.add(tailImage);
        }

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroller.setContent(horizontalLayout);
        add(scroller);
        add(new H2(tailById.getType()));
        add(new Paragraph(tailById.getDescription()));

    }
}
