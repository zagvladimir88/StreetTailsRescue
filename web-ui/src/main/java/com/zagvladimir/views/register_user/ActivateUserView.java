package com.zagvladimir.views.register_user;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.MainLayout;

@PageTitle("Активация аккаунта")
@Route(value = "activate/:activateCode?", layout = MainLayout.class)
@AnonymousAllowed
public class ActivateUserView extends VerticalLayout implements BeforeEnterObserver {

    private final UserService userService;

    public ActivateUserView(UserService userService) {
        this.userService = userService;
        setSpacing(false);
        Image img = new Image("images/logo.png", "placeholder plant");
        img.setWidth("400px");
        add(img);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String activateCode = null;
        boolean isActivated = false;
        if(beforeEnterEvent.getRouteParameters().get("activateCode").isPresent()){
        activateCode = beforeEnterEvent.getRouteParameters().get("activateCode").get();
        isActivated = userService.activateUser(activateCode);
        }

        if(isActivated){
            add(new H2("Thank you for registration"));
            add(new Paragraph("This is the place where you can find your furry friend.🤗"));
        } else {
            add(new H2(" Activation code is invalid"));
        }


    }
}
