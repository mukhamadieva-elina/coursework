package com.company.web.component.customSetter;

import com.company.entity.Warehouse2;
import com.company.service.GoodsService;
import com.vaadin.flow.data.binder.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodIdSetterForWh2 implements Setter<Warehouse2, String> {
    @Autowired
    GoodsService goodsService;

    @Override
    public void accept(Warehouse2 warehouse2, String s) {
        if (!(s.isEmpty())) {
            warehouse2.setGoodId(goodsService.readGood(Long.parseLong(s)));
        }
    }
}
