package com.zagvladimir.views.register_user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zagvladimir.model.City;
import com.zagvladimir.model.User;
import com.zagvladimir.service.city.CityService;
import com.zagvladimir.service.user.UserServiceImpl;
import com.zagvladimir.views.MainLayout;


@PageTitle("Person Form")
@Route(value = "person-form", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class RegisterFormView extends Div {

    private final transient CityService cityService;
    private final transient UserServiceImpl userService;

    private final TextField firstName = new TextField("First name");
    private final TextField login = new TextField("Login");
    private final EmailField email = new EmailField("Email address");
    private final PasswordField password = new PasswordField("Password");
    private final Select<String> cityField = new Select<>();
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");


    public RegisterFormView(CityService cityService, UserServiceImpl userService) {
        this.cityService = cityService;
        this.userService = userService;


        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        clearForm();
    }

    private void clearForm() {
        firstName.clear();
        login.clear();
        email.clear();
        password.clear();
        cityField.setValue("Жлобин");
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, login, password, email, cityField);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");


        cityField.setLabel("City");
        cityField.setItems(cityService.getAllCityOrderByName().stream().map(City::getName).toList());


        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            registerNewUser();
            Notification.show(" details stored.");
            clearForm();
        });

        cancel.addClickListener(e -> clearForm());
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void registerNewUser() {
        User newUser = new User();
        newUser.setFirstName(firstName.getValue());
        newUser.setLogin(login.getValue());
        newUser.setPassword(password.getValue());
        newUser.setEmail(email.getValue());
        newUser.setCity(cityService.findCityByName(cityField.getValue()));
        userService.register(newUser);
    }
}
