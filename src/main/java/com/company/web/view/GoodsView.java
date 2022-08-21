package com.company.web.view;

import com.company.web.component.GoodsEditor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import com.company.entity.Goods;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.GoodsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Route("/client/goods")
public class GoodsView extends VerticalLayout {

    private final GoodsService goodsService;
    private final JwtTokenProvider jwtTokenProvider;

    private final Grid<Goods> grid = new Grid<>(Goods.class);
    private final TextField filter = new TextField("", "Type to filter...");
    private final IntegerField utility = new IntegerField("", "Utility field...");
    private final TextField token = new TextField("", "Type your token...");
    private final Button addNewBtn = new Button("Add new");
    private final Button deleteAllBtn = new Button("Delete all");
    private final Button byGoodsIdBtn = new Button("By Good's ID");
    private final HorizontalLayout toolbar =
            new HorizontalLayout(filter, utility, token, addNewBtn, deleteAllBtn, byGoodsIdBtn);
    private final GoodsEditor editor;

    @Autowired
    public GoodsView(GoodsService goodsService, GoodsEditor editor, JwtTokenProvider jwtTokenProvider) {
        this.goodsService = goodsService;
        this.editor = editor;
        this.jwtTokenProvider = jwtTokenProvider;

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showGood(e.getValue()));

        add(toolbar, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editGood(e.getValue());
            }
        });

        addNewBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                editor.editGood(new Goods());
            }
        });

        deleteAllBtn.addClickListener(e -> {
            if (jwtTokenProvider.checkToken(token.getValue())) {
                goodsService.deleteAllGoods();
            }
        });

        byGoodsIdBtn.addClickListener(e -> {
            //if (jwtTokenProvider.checkToken(token.getValue())) {
                grid.setItems(goodsService.readGood(utility.getValue().longValue()));
            //}
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showGood(filter.getValue());
        });

        showGood("");
    }

    private void showGood(String name) {
        if (name.isEmpty()) {
            grid.setItems(goodsService.readAllGoods());
        } else {
            try {
                grid.setItems(goodsService.readGoodsStartWith(name));
            } catch (IllegalStateException e) {
                grid.setItems(Collections.emptyList());
            }
        }
    }
}

