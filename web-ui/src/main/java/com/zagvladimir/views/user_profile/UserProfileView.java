package com.zagvladimir.views.user_profile;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zagvladimir.model.Tail;
import com.zagvladimir.service.tail.TailService;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.MainLayout;

import java.util.List;

@PageTitle("Профиль")
@Route(value = "profile/:userID?", layout = MainLayout.class)
public class UserProfileView extends VerticalLayout implements BeforeEnterObserver{

    private final UserService userService;
    private final TailService tailService;

    public UserProfileView(UserService userService, TailService tailService) {
        this.userService = userService;
        this.tailService = tailService;
        setSpacing(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String userID = beforeEnterEvent.getRouteParameters().get("userID").
                orElse("22");

        List<Tail> tails = userService.getAllTails(Integer.valueOf(userID));

        Grid<Tail> grid = new Grid<>();
        grid.setItems(tails);
        grid.addColumn(Tail::getType).setHeader("Вид");
        grid.addColumn(Tail::getAddress).setHeader("Адресс");
        grid.addColumn(Tail::getCity).setHeader("город");
        grid.addColumn(Tail::getDescription).setHeader("описание");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, tail) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> tailService.softDeleteTailById(tail.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Удалить");

        Div tailInfo = new Div(grid);

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Персональная информация",
                new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Список ваших хвостатых",
                tailInfo);
        add(tabSheet);
    }
}
