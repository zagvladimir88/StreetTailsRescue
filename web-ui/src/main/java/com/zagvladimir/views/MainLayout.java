package com.zagvladimir.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zagvladimir.components.appnav.AppNav;
import com.zagvladimir.components.appnav.AppNavItem;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.about.AboutView;
import com.zagvladimir.views.admin.AdminPanelView;
import com.zagvladimir.views.create_tail.CreateTailFormView;
import com.zagvladimir.views.login.LoginView;
import com.zagvladimir.views.register_user.RegisterFormView;
import com.zagvladimir.views.tails_list.TailsListView;
import com.zagvladimir.views.user_profile.UserProfileView;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "")
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private final Authentication authentication;
    private final transient AuthenticationContext authContext;
    private final transient UserService userService;
    private H2 viewTitle;

    public MainLayout(AuthenticationContext authContext, UserService userService) {
        this.authContext = authContext;
        this.userService = userService;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.AUTO);

        Button loginButton = new Button("Войти");
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        loginButton.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));

        Button registerButton = new Button("Зарегистрироваться");
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        registerButton.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.AlignItems.END);
        registerButton.addClickListener(e -> UI.getCurrent().navigate(RegisterFormView.class));
        addToNavbar(true, toggle, viewTitle);
        addToNavbar(loginButton);
        addToNavbar(registerButton);

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            loginButton.setVisible(false);
            registerButton.setVisible(false);

            Button logOut = new Button("Выйти");
            logOut.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            logOut.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.AlignItems.END);
            logOut.addClickListener(e -> authContext.logout());
            logOut.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.AlignItems.END);
            addToNavbar(logOut);
        }


    }

    private void addDrawerContent() {
        H1 appName = new H1("Street Tails Rescue");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Хвостатые", TailsListView.class, "la la-peace"));
        nav.addItem(new AppNavItem("О нас", AboutView.class, "la la-file"));

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            nav.addItem(new AppNavItem("Профиль", UserProfileView.class, "la la-user"));
            nav.addItem(new AppNavItem("Добавить хвостатого", CreateTailFormView.class, "la la-gitlab"));
            if (userService.isUserAdmin(authentication.getName())) {
                nav.addItem(new AppNavItem("Администрирование", AdminPanelView.class, "la la-user-check"));
            }
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
