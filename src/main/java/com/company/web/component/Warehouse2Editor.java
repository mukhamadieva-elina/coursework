package com.company.web.component;

import com.company.entity.Warehouse2;
import com.company.exception.IncorrectDataInput;
import com.company.repository.Warehouse2Repository;
import com.company.service.GoodsService;
import com.company.web.component.customSetter.GoodIdSetterForWh2;
import com.company.web.component.customValueProvider.GoodIdValueProviderForWh2;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class Warehouse2Editor extends VerticalLayout implements KeyNotifier {

    private final Warehouse2Repository warehouse2Repository;
    private final GoodsService goodsService;

    private Warehouse2 warehouse2;

    Binder<Warehouse2> binder = new Binder<>(Warehouse2.class);
    TextField goodId = new TextField("Good Id");
    TextField goodCount = new TextField("Good Count");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    @Setter
    private Warehouse2Editor.ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public Warehouse2Editor(Warehouse2Repository warehouse2Repository, GoodsService goodsService) {
        this.warehouse2Repository = warehouse2Repository;
        this.goodsService = goodsService;
        add(goodId, goodCount, actions);
        binder.bind(goodId, new GoodIdValueProviderForWh2(), new GoodIdSetterForWh2());
        binder.forField(goodCount)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Enter number"))
                .bind("goodCount");
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void delete() {
        warehouse2Repository.delete(warehouse2);
        changeHandler.onChange();
    }

    private void save() {
        if (!goodId.isEmpty()) {
            warehouse2.setGoodId(goodsService.readGood(Long.parseLong(goodId.getValue())));
        }
        if (!goodCount.isEmpty()) {
            warehouse2.setGoodCount(Long.parseLong(goodCount.getValue()));
        }
        try {
            isValidGoodCount(goodCount.getValue());
            warehouse2Repository.save(warehouse2);
            changeHandler.onChange();
        } catch (IncorrectDataInput e) {
            Notification notification = new Notification();
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div statusText = new Div(new Text("Incorrect input! Please, check Good Count" +
                    "\nGood Count can be between 0 and 500!"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(statusText, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.open();
        }
    }

    public void editWarehouse2(Warehouse2 warehouse2) {
        if (warehouse2 == null) {
            setVisible(false);
            return;
        }

        if (warehouse2.getId() != null) {
            this.warehouse2 = warehouse2Repository.findById(warehouse2.getId()).orElse(warehouse2);
        } else {
            this.warehouse2 = warehouse2;
        }
        binder.setBean(this.warehouse2);
        setVisible(true);
    }

    static void isValidGoodCount(String goodCount) {
        if (!goodCount.isEmpty()) {
            if (!(Long.parseLong(goodCount) >= 0 && Long.parseLong(goodCount) <= 500)) {
                throw new IncorrectDataInput("incorrect goodCount");
            }
        }
    }
}