package com.company.web.component.customValueProvider;

import com.company.entity.Sales;
import com.vaadin.flow.function.ValueProvider;

public class GoodIdValueProvider implements ValueProvider<Sales, String> {

    @Override
    public String apply(Sales sales) {
        if (sales.getGoodId() != null) {
            return sales.getGoodId().getId().toString();
        }
        return null;
    }
}
