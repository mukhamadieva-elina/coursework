package com.company.web.view;

import com.company.entity.Warehouse1;
import com.company.exception.NonexistentElementException;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.Warehouse1Service;
import com.company.web.component.Warehouse1Editor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Route("client/warehouses/1")
public class Warehouse1View extends VerticalLayout {
    private final Warehouse1Service warehouse1Service;
    private final JwtTokenProvider jwtTokenProvider;

    private final Grid<Warehouse1> grid = new Grid<>(Warehouse1.class);
    private final IntegerField filter = new IntegerField("", "Type to filter...");
    private final TextField token = new TextField("", "Type your token...");
    private final Button addNewBtn = new Button("Add new");
    private final Button deleteAllBtn = new Button("Delete all");
    private final HorizontalLayout toolbar =
            new HorizontalLayout(
                    filter, token,
                    addNewBtn, deleteAllBtn
            );

    private final Warehouse1Editor editor;

    @Autowired
    public Warehouse1View(Warehouse1Service warehouse1Service, Warehouse1Editor editor, JwtTokenProvider jwtTokenProvider) {
        this.warehouse1Service = warehouse1Service;
        this.editor = editor;
        this.jwtTokenProvider = jwtTokenProvider;

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                showInWh1(e.getValue().toString());
            } else {
                showInWh1("");
            }
        });

        add(toolbar, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editWarehouse1(e.getValue());
            }
        });
        addNewBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editWarehouse1(new Warehouse1());
            }
        });

        deleteAllBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                warehouse1Service.deleteAllInWh1();
                grid.setItems(warehouse1Service.readAllInWh1());
            }
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            if (filter.getValue() != null) {
                showInWh1(filter.getValue().toString());
            } else {
                showInWh1("");
            }
        });

        showInWh1("");
    }
    private void showInWh1(String goodId) {
        if (goodId.isEmpty()) {
            grid.setItems(warehouse1Service.readAllInWh1());
        } else {
            try {
                grid.setItems(warehouse1Service.getInWh1OfGood(Long.parseLong(goodId)));
            } catch (IllegalStateException | NonexistentElementException e) {
                grid.setItems(Collections.emptyList());
            }
        }
    }
}
