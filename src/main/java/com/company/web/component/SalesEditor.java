package com.company.web.component;

import com.company.entity.Sales;
import com.company.exception.IncorrectDataInput;
import com.company.repository.SalesRepository;
import com.company.service.GoodsService;
import com.company.web.component.customSetter.GoodIdSetter;
import com.company.web.component.customValueProvider.GoodIdValueProvider;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
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
public class SalesEditor extends VerticalLayout implements KeyNotifier {

    private final SalesRepository salesRepository;
    private final GoodsService goodsService;

    private Sales sale;

    Binder<Sales> binder = new Binder<>(Sales.class);
    TextField goodId = new TextField("Good Id");
    TextField goodCount = new TextField("Good Count");
    DateTimePicker createDate = new DateTimePicker("Create Date");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    @Setter
    private SalesEditor.ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public SalesEditor(SalesRepository salesRepository, GoodsService goodsService) {
        this.salesRepository = salesRepository;
        this.goodsService = goodsService;
        add(goodId, goodCount, createDate, actions);
        binder.bind(goodId, new GoodIdValueProvider(), new GoodIdSetter());
        binder.forField(goodCount)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Enter number"))
                        .bind("goodCount");
        binder.bind(createDate, "createDate");
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
        salesRepository.delete(sale);
        changeHandler.onChange();
    }

    private void save() {
        if (!goodId.isEmpty()) {
            sale.setGoodId(goodsService.readGood(Long.parseLong(goodId.getValue())));
        }
        if (!goodCount.isEmpty()) {
            sale.setGoodCount(Long.parseLong(goodCount.getValue()));
        }
        if(!createDate.isEmpty()) {
            sale.setCreateDate(createDate.getValue());
        }
        try {
            isSalesCountValid(goodCount.getValue());
            salesRepository.save(sale);
        } catch (IncorrectDataInput e) {
            Notification notification = new Notification();
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div statusText = new Div(new Text("Incorrect input! Please, check Good Count" +
                    "\nGood Count can be between 0 and 200!"));

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
        changeHandler.onChange();
    }

    public void editSale(Sales sale) {
        if (sale == null) {
            setVisible(false);
            return;
        }

        if (sale.getId() != null) {
            this.sale = salesRepository.findById(sale.getId()).orElse(sale);
        } else {
            this.sale = sale;
        }
        binder.setBean(this.sale);
        setVisible(true);
    }

    static void isSalesCountValid(String goodCount) {
        if (!goodCount.isEmpty()) {
            if (!(Long.parseLong(goodCount) > 0 && Long.parseLong(goodCount) <= 200)) {
                throw new IncorrectDataInput("incorrect goodCount");
            }
        }
    }
}
