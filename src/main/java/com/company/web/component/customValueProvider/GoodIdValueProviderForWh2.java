package com.company.web.component.customValueProvider;

import com.company.entity.Warehouse2;
import com.vaadin.flow.function.ValueProvider;

public class GoodIdValueProviderForWh2 implements ValueProvider<Warehouse2, String> {

    @Override
    public String apply(Warehouse2 warehouse2) {
        if (warehouse2.getGoodId() != null) {
            return warehouse2.getGoodId().getId().toString();
        }
        return null;
    }
}
