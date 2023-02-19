package com.zagvladimir.views.create_tail;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zagvladimir.model.City;
import com.zagvladimir.model.Tail;
import com.zagvladimir.service.city.CityService;
import com.zagvladimir.service.image.ImageService;
import com.zagvladimir.service.tail.TailService;
import com.zagvladimir.service.user.UserService;
import com.zagvladimir.views.MainLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.io.InputStream;

@PageTitle("Добавление нового хвостатого")
@Route(value = "tail-form", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class CreateTailFormView extends Div {
    private final transient CityService cityService;
    private final transient TailService tailService;
    private final transient UserService userService;

    private final TextField type = new TextField("Вид хвостатого");
    private final Select<String> city = new Select<>();
    private final TextField address = new TextField("Адресс хвостатого");
    private final TextField description = new TextField("Описание");

    private final Button cancel = new Button("Отменить");
    private final Button save = new Button("Сохранить");

    private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private final transient ImageService imageService;
    private byte[] tailImage;
    private Authentication authentication;

    public CreateTailFormView(CityService cityService, TailService tailService, ImageService imageService, UserService userService) {
        this.cityService = cityService;
        this.userService = userService;
        authentication = SecurityContextHolder.getContext().getAuthentication();


        this.imageService = imageService;
        this.tailService = tailService;


        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(uploadImage());
        clearForm();

    }

    private void clearForm() {
        type.clear();
        city.setValue("Жлобин");
        address.clear();
        description.clear();
    }


    private Upload uploadImage() {
        tailImage = null;
        Upload upload = new Upload(buffer);
        upload.setMaxFiles(1);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            try {
                tailImage = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return upload;
    }

    private Component createTitle() {
        return new H3("Информация о хвостатом");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(type, city, address, description);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        city.setLabel("City");
        city.setItems(cityService.getAllCityOrderByName().stream().map(City::getName).toList());

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            createNewTail();
            Notification.show(" Tails stored.");
            clearForm();
        });

        cancel.addClickListener(e -> clearForm());

        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }


    private void createNewTail() {
        Tail newTail = new Tail();
        newTail.setFinder(userService.findUserByLogin(authentication.getName()).get());
        newTail.setType(type.getValue());
        newTail.setCity(cityService.findCityByName(city.getValue()));
        newTail.setAddress(address.getValue());
        newTail.setDescription(description.getValue());

        Integer newTailId = tailService.create(newTail);
        imageService.uploadFile(tailImage, newTailId, ".jpg");
    }
}
