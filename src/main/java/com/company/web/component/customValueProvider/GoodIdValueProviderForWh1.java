package com.company.web.component.customValueProvider;

import com.company.entity.Warehouse1;
import com.vaadin.flow.function.ValueProvider;

public class GoodIdValueProviderForWh1 implements ValueProvider<Warehouse1, String> {

    @Override
    public String apply(Warehouse1 warehouse1) {
        if (warehouse1.getGoodId() != null) {
            return warehouse1.getGoodId().getId().toString();
        }
        return null;
    }
}
