package com.zagvladimir.views.tails_list;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.zagvladimir.model.City;
import com.zagvladimir.model.Tail;
import com.zagvladimir.service.city.CityService;
import com.zagvladimir.service.image.ImageService;
import com.zagvladimir.service.tail.TailServiceImpl;
import com.zagvladimir.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@PageTitle("Хвостатые")
@Route(value = "tail-list/:citySort?", layout = MainLayout.class)
@AnonymousAllowed
public class TailsListView extends Main implements HasComponents, HasStyle, BeforeEnterObserver {

    private final transient TailServiceImpl tailService;
    private final transient ImageService imageService;
    private final transient CityService cityService;
    private OrderedList imageContainer;
    private transient Optional<String> citySortParam;

    @Autowired
    public TailsListView(TailServiceImpl tailService, ImageService imageService, CityService cityService) {
        this.tailService = tailService;
        this.imageService = imageService;
        this.cityService = cityService;

        constructUI();

    }

    private void createTailList() {
        List<Tail> tailList = this.tailService.findAllWithStatusActive();


        if (citySortParam.isPresent()) {
            tailList = tailList.stream()
                    .filter(tail -> tail.getCity().getName().equals(citySortParam.get()))
                    .toList();
        }


        for (Tail tail : tailList) {
            var urls = this.imageService.getUrls(tail.getId());
            String url;
            if (urls.isEmpty()) {
                url = "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80";

            } else url = urls.get(0).toString();

            var imageListViewCard = new TailsCardView(tail.getDescription(),
                    tail.getType(),
                    tail.getAddress(),
                    tail.getCity(),
                    url,
                    tail.getId());

            imageContainer.add(imageListViewCard);
        }

    }

    private void constructUI() {
        addClassNames("image-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Все хвосты");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Города");
        sortBy.setItems(cityService.getCitiesWithTails()
                .stream()
                .map(City::getName)
                .toList());
        sortBy.addValueChangeListener(event -> {
            imageContainer.removeAll();
            sortBy.getUI().ifPresent(ui -> ui.navigate(
                    TailsListView.class,
                    new RouteParameters("citySort", sortBy.getValue())));

        });

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer, sortBy);
        add(container, imageContainer);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        citySortParam = beforeEnterEvent.getRouteParameters().get("citySort");
        createTailList();
    }
}
