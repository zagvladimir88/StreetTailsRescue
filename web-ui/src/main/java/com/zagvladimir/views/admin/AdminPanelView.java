package com.zagvladimir.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;
import com.zagvladimir.service.tail.TailService;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.MainLayout;
import lombok.SneakyThrows;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Панель администратора")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed({"ROLE_ADMIN"})
public class AdminPanelView extends VerticalLayout {
    private final transient UserService userService;
    private final transient TailService tailService;

    public AdminPanelView(UserService userService, TailService tailService) {
        this.userService = userService;
        this.tailService = tailService;

        setSpacing(false);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("100%");
        tabSheet.add("Список пользователей",
                userInfoTab());
        tabSheet.add("Список хвостатых",
                tailInfoTab());
        add(tabSheet);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private Div tailInfoTab() {
        List<Tail> tails = tailService.findAll();

        Grid<Tail> grid = new Grid<>();
        grid.setItems(tails);
        grid.addColumn(Tail::getType).setHeader("Вид");
        grid.addColumn(Tail::getAddress).setHeader("Адресс");
        grid.addColumn(tail -> tail.getCity().getName()).setHeader("город").setAutoWidth(true);;
        grid.addColumn(Tail::getDescription).setHeader("описание");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, tail) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> tailService.deleteById(tail.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Удаление");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> userService.softDeleteUserById(user.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Мягкое удаление");
        return new Div(grid);
    }

    @SneakyThrows
    private Div userInfoTab() {
        List<User> users = userService.findAll();

        Grid<User> grid = new Grid<>();
        grid.setItems(users);
        grid.addColumn(User::getFirstName).setHeader("Имя");
        grid.addColumn(User::getLogin).setHeader("Логин");
        grid.addColumn(User::getEmail).setHeader("email").setAutoWidth(true);
        grid.addColumn(user -> user.getCity().getName()).setHeader("Город").setAutoWidth(true);;
        grid.addColumn(User::getStatus).setHeader("Статус");

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> userService.deleteUserById(user.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Удаление");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> userService.softDeleteUserById(user.getId()));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Мягкое удаление");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> userService.banUser(user.getId()));
                    button.setIcon(new Icon(VaadinIcon.BAN));
                })).setHeader("Бан");
        return new Div(grid);
    }
}
