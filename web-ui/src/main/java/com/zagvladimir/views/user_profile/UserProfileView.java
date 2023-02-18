package com.zagvladimir.views.user_profile;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zagvladimir.model.Tail;
import com.zagvladimir.service.tail.TailService;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.MainLayout;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Профиль")
@Route(value = "profile", layout = MainLayout.class)
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class UserProfileView extends VerticalLayout {
    private final transient UserService userService;
    private final transient TailService tailService;
    private final Authentication authentication;
    private Integer userID;

    public UserProfileView(UserService userService, TailService tailService) {
        this.userService = userService;
        this.tailService = tailService;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        setSpacing(false);

        userID = userService.findUserByLogin(authentication.getName()).get().getId();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("100%");
        tabSheet.add("Персональная информация",
                userInfoTab());
        tabSheet.add("Список ваших хвостатых",
                tailInfoTab());
        add(tabSheet);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private Div tailInfoTab() {
        List<Tail> tails = userService.getAllTails(userID);

        Grid<Tail> grid = new Grid<>();
        grid.setItems(tails);
        grid.addColumn(Tail::getType).setHeader("Вид");
        grid.addColumn(Tail::getAddress).setHeader("Адресс");
        grid.addColumn(tail -> tail.getCity().getName()).setHeader("город");
        grid.addColumn(Tail::getDescription).setHeader("описание");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, tail) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> tailService.softDeleteTailById(tail.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Удалить");

        return new Div(grid);
    }

    @SneakyThrows
    private VerticalLayout userInfoTab() {
        var userById = userService.findById(userID);
        Span name = new Span(String.format("Имя: %s", userById.getFirstName()));
        Span login = new Span(String.format("Логин: %s", userById.getLogin()));
        Span email = new Span(String.format("Электронный адресс: %s", userById.getEmail()));
        Span city = new Span(String.format("Город: %s", userById.getCity().getName()));
        Span tailsCount = new Span(String.format("Добавлено хвостов: %s", userById.getTails().size()));

        return new VerticalLayout(name, login, email, city, tailsCount);
    }
}
