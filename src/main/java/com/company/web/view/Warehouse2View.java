package com.company.web.view;

import com.company.entity.Warehouse2;
import com.company.exception.NonexistentElementException;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.Warehouse2Service;
import com.company.web.component.Warehouse2Editor;
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

@Route("client/warehouses/2")
public class Warehouse2View extends VerticalLayout {
    private final Warehouse2Service warehouse2Service;
    private final JwtTokenProvider jwtTokenProvider;

    private final Grid<Warehouse2> grid = new Grid<>(Warehouse2.class);
    private final IntegerField filter = new IntegerField("", "Type to filter...");
    private final TextField token = new TextField("", "Type your token...");
    private final Button addNewBtn = new Button("Add new");
    private final Button deleteAllBtn = new Button("Delete all");
    private final HorizontalLayout toolbar =
            new HorizontalLayout(
                    filter, token,
                    addNewBtn, deleteAllBtn
            );

    private final Warehouse2Editor editor;

    @Autowired
    public Warehouse2View(Warehouse2Service warehouse2Service, Warehouse2Editor editor, JwtTokenProvider jwtTokenProvider) {
        this.warehouse2Service = warehouse2Service;
        this.editor = editor;
        this.jwtTokenProvider = jwtTokenProvider;

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                showInWh2(e.getValue().toString());
            } else {
                showInWh2("");
            }
        });

        add(toolbar, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editWarehouse2(e.getValue());
            }
        });
        addNewBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editWarehouse2(new Warehouse2());
            }
        });

        deleteAllBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                warehouse2Service.deleteAllInWh2();
                grid.setItems(warehouse2Service.readAllInWh2());
            }
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            if (filter.getValue() != null) {
                showInWh2(filter.getValue().toString());
            } else {
                showInWh2("");
            }
        });

        showInWh2("");
    }
    private void showInWh2(String goodId) {
        if (goodId.isEmpty()) {
            grid.setItems(warehouse2Service.readAllInWh2());
        } else {
            try {
                grid.setItems(warehouse2Service.getInWh2OfGood(Long.parseLong(goodId)));
            } catch (IllegalStateException | NonexistentElementException e) {
                grid.setItems(Collections.emptyList());
            }
        }
    }
}
