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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zagvladimir.model.Tail;
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
@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
public class CreateTailFormView extends Div {

    private final TextField type = new TextField("Вид хвостатого");
    private final TextField city = new TextField("Город");
    private final TextField address = new TextField("Адресс хвостатого");
    private final TextField description = new TextField("Описание");

    private final Button cancel = new Button("Отменить");
    private final Button save = new Button("Сохранить");

    private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private byte[] tailImage;
    private final transient ImageService imageService;

    private final Binder<Tail> binder = new Binder<>(Tail.class);
    private final transient TailService tailService;
    private final transient UserService userService;
    private Authentication authentication;

    public CreateTailFormView(TailService tailService, ImageService imageService, UserService userService) {
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

        binder.bindInstanceFields(this);

    }

    private void clearForm() {
        binder.setBean(new Tail());
    }

    private Upload uploadImage(){
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

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            Tail tail = binder.getBean();
            tail.setFinder(userService.findUserByLogin(authentication.getName()).get());
            Integer newTailId = tailService.create(tail);
            imageService.uploadFile(tailImage,newTailId,".jpg");
            Notification.show(binder.getBean().getClass().getSimpleName() + " Tails stored.");
            clearForm();
        });

        cancel.addClickListener(e -> clearForm());

        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}
