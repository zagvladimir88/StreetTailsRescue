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
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zagvladimir.components.appnav.AppNav;
import com.zagvladimir.components.appnav.AppNavItem;
import com.zagvladimir.views.about.AboutView;
import com.zagvladimir.views.create_tail.CreateTailFormView;
import com.zagvladimir.views.tails_list.TailsListView;
import com.zagvladimir.views.register_user.RegisterFormView;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value = "")
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.AUTO);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        registerButton.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.AlignItems.END);
        registerButton.addClickListener(e -> {
            UI.getCurrent().navigate(RegisterFormView.class);
        });

        registerButton.setVisible(true);

        addToNavbar(true, toggle, viewTitle);
        addToNavbar(loginButton);
        addToNavbar(registerButton);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Street Tails Rescue");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Хвостатые", TailsListView.class, "la la-peace"));
        nav.addItem(new AppNavItem("О нас", AboutView.class, "la la-file"));
        nav.addItem(new AppNavItem("Добавить хвостатого", CreateTailFormView.class, "la la-user"));

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
