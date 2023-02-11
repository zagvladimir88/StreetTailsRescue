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
import com.zagvladimir.views.MainLayout;

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
    private final Upload upload = new Upload(buffer);
    private final Binder<Tail> binder = new Binder<>(Tail.class);
    private byte[] tailImage;
    private final ImageService imageService;

    public CreateTailFormView(TailService tailService, ImageService imageService) {
        this.imageService = imageService;

        tailImage = null;
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            try {
                tailImage = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(upload);
        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            Integer newTailId = tailService.create(binder.getBean());
            imageService.uploadFile(tailImage,newTailId,".jpg");
            Notification.show(binder.getBean().getClass().getSimpleName() + " Tails stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new Tail());
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
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}
