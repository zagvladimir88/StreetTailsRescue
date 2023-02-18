package com.zagvladimir.views.tails_list;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.zagvladimir.model.City;
import com.zagvladimir.views.tail.TailsView;

public class TailsCardView extends ListItem {

    public TailsCardView(String tailDescription, String type, String address, City tailCity, String url, Integer id) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt("alt");

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(type);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(tailCity.getName() + ": " + address);

        Paragraph description = new Paragraph(tailDescription);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Label");

        var routerLink = new RouterLink("Подробнее",
                TailsView.class, new RouteParameters("tailID", id.toString()));

        add(div, header, subtitle, description, routerLink);

    }
}
