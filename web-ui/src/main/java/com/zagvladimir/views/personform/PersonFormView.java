//package com.zagvladimir.views.personform;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.customfield.CustomField;
//import com.vaadin.flow.component.datepicker.DatePicker;
//import com.vaadin.flow.component.dependency.Uses;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.H3;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.textfield.EmailField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.zagvladimir.model.User;
//import com.zagvladimir.views.MainLayout;
//
//@PageTitle("Person Form")
//@Route(value = "person-form", layout = MainLayout.class)
//@Uses(Icon.class)
//public class PersonFormView extends Div {
//
//    private TextField firstName = new TextField("First name");
//    private TextField lastName = new TextField("Last name");
//    private EmailField email = new EmailField("Email address");
//    private DatePicker dateOfBirth = new DatePicker("Birthday");
//    private PhoneNumberField phone = new PhoneNumberField("Phone number");
//    private TextField occupation = new TextField("Occupation");
//
//    private Button cancel = new Button("Cancel");
//    private Button save = new Button("Save");
//
//    private Binder<User> binder = new Binder<>(User.class);
//
//    public PersonFormView(SamplePersonService personService) {
//        addClassName("person-form-view");
//
//        add(createTitle());
//        add(createFormLayout());
//        add(createButtonLayout());
//
//        binder.bindInstanceFields(this);
//        clearForm();
//
//        cancel.addClickListener(e -> clearForm());
//        save.addClickListener(e -> {
//            personService.update(binder.getBean());
//            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
//            clearForm();
//        });
//    }
//
//    private void clearForm() {
//        binder.setBean(new SamplePerson());
//    }
//
//    private Component createTitle() {
//        return new H3("Personal information");
//    }
//
//    private Component createFormLayout() {
//        FormLayout formLayout = new FormLayout();
//        email.setErrorMessage("Please enter a valid email address");
//        formLayout.add(firstName, lastName, dateOfBirth, phone, email, occupation);
//        return formLayout;
//    }
//
//    private Component createButtonLayout() {
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.addClassName("button-layout");
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        buttonLayout.add(save);
//        buttonLayout.add(cancel);
//        return buttonLayout;
//    }
//
//    private static class PhoneNumberField extends CustomField<String> {
//        private ComboBox<String> countryCode = new ComboBox<>();
//        private TextField number = new TextField();
//
//        public PhoneNumberField(String label) {
//            setLabel(label);
//            countryCode.setWidth("120px");
//            countryCode.setPlaceholder("Country");
//            countryCode.setAllowedCharPattern("[\\+\\d]");
//            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
//            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
//            number.setAllowedCharPattern("\\d");
//            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
//            layout.setFlexGrow(1.0, number);
//            add(layout);
//        }
//
//        @Override
//        protected String generateModelValue() {
//            if (countryCode.getValue() != null && number.getValue() != null) {
//                String s = countryCode.getValue() + " " + number.getValue();
//                return s;
//            }
//            return "";
//        }
//
//        @Override
//        protected void setPresentationValue(String phoneNumber) {
//            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
//            if (parts.length == 1) {
//                countryCode.clear();
//                number.setValue(parts[0]);
//            } else if (parts.length == 2) {
//                countryCode.setValue(parts[0]);
//                number.setValue(parts[1]);
//            } else {
//                countryCode.clear();
//                number.clear();
//            }
//        }
//    }
//
//}
