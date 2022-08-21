package com.company.web.component.customSetter;

import com.company.entity.Warehouse1;
import com.company.service.GoodsService;
import com.vaadin.flow.data.binder.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodIdSetterForWh1 implements Setter<Warehouse1, String> {
    @Autowired
    GoodsService goodsService;

    @Override
    public void accept(Warehouse1 warehouse1, String s) {
        if (!(s.isEmpty())) {
            warehouse1.setGoodId(goodsService.readGood(Long.parseLong(s)));
        }
    }
}
