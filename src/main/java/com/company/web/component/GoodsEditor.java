package com.company.web.component;

import com.company.entity.Goods;
import com.company.exception.IncorrectDataInput;
import com.company.repository.GoodsRepository;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class GoodsEditor extends VerticalLayout implements KeyNotifier {
    private final GoodsRepository goodsRepository;

    private Goods good;

    Binder<Goods> binder = new Binder<>(Goods.class);
    TextField name = new TextField("Name");
    NumberField priority = new NumberField("Priority");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public GoodsEditor(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
        add(name, priority, actions);
        binder.bindInstanceFields(this);
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
        goodsRepository.delete(good);
        changeHandler.onChange();
    }

    private void save() {
        try {
            isGoodsNameValid(good.getName());
            isGoodsPriorityValid(good.getPriority());
            goodsRepository.save(good);
            changeHandler.onChange();
        } catch (IncorrectDataInput e) {
            Notification notification = new Notification();
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div statusText = new Div(new Text("Incorrect input! Please, check Name and Priority" +
                    "\nPriority can be between 0 and 10!" +
                    "\nName consists of 3-50 symbols!"));

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

    public void editGood(Goods good) {
        if (good == null) {
            setVisible(false);
            return;
        }

        if (good.getId() != null) {
            this.good = goodsRepository.findById(good.getId()).orElse(good);
        } else {
            this.good = good;
        }
        binder.setBean(this.good);
        setVisible(true);
    }

    static void isGoodsNameValid(String name) {
        if (!name.matches("^[A-z0-9 -]{3,50}")) {
            throw new IncorrectDataInput("incorrect good's name");
        }
    }

    static void isGoodsPriorityValid(Double priority) {
        if (!(priority > 0 && priority <= 10)) {
            throw new IncorrectDataInput("incorrect good's priority");
        }
    }
}
