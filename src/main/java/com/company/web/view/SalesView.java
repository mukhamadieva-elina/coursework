package com.company.web.view;

import com.company.entity.Sales;
import com.company.exception.NonexistentElementException;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.SalesService;
import com.company.web.component.SalesEditor;
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

@Route("client/sales")
public class SalesView extends VerticalLayout {
    private final SalesService salesService;
    private final JwtTokenProvider jwtTokenProvider;

    private final Grid<Sales> grid = new Grid<>(Sales.class);
    private final IntegerField filter = new IntegerField("", "Type to filter...");
    private final TextField token = new TextField("", "Type your token...");
    private final Button addNewBtn = new Button("Add new");
    private final Button deleteAllBtn = new Button("Delete all");
    private final HorizontalLayout toolbar =
            new HorizontalLayout(
                    filter, token,
                    addNewBtn, deleteAllBtn
            );

    private final SalesEditor editor;

    @Autowired
    public SalesView(SalesService salesService, SalesEditor editor, JwtTokenProvider jwtTokenProvider) {
        this.salesService = salesService;
        this.editor = editor;
        this.jwtTokenProvider = jwtTokenProvider;

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                showSales(e.getValue().toString());
            } else {
                showSales("");
            }
        });

        add(toolbar, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editSale(e.getValue());
            }
        });
        addNewBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editSale(new Sales());
            }
        });

        deleteAllBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                salesService.deleteAllSales();
                grid.setItems(salesService.readAllSales());
            }
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            if (filter.getValue() != null) {
                showSales(filter.getValue().toString());
            } else {
                showSales("");
            }
        });

        showSales("");
    }
    private void showSales(String goodId) {
        if (goodId.isEmpty()) {
            grid.setItems(salesService.readAllSales());
        } else {
            try {
                grid.setItems(salesService.getSalesOfGood(Long.parseLong(goodId)));
            } catch (IllegalStateException | NonexistentElementException e) {
                grid.setItems(Collections.emptyList());
            }
        }
    }
}
