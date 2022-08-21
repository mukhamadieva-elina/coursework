package com.company.web.component.customSetter;

import com.company.entity.Sales;
import com.company.service.GoodsService;
import com.vaadin.flow.data.binder.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodIdSetter implements Setter<Sales, String> {
    @Autowired
    GoodsService goodsService;

    @Override
    public void accept(Sales sales, String s) {
        if (!(s.isEmpty())) {
            sales.setGoodId(goodsService.readGood(Long.parseLong(s)));
        }
    }
}
