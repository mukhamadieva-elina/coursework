package com.company.service;

import com.company.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {

    void createInWh1(Long goodId, Long goodCount);
    Warehouse1 readInWh1(Long id);
    List<Warehouse1> readAllInWh1();
    void updateInWh1(Long id, Long goodCount);
    void deleteInWh1(Long id);
    void deleteAllInWh1();
    void deleteEmptyInWh1();
    List<Warehouse1> getInWh1OfGood(Long goodId);
}
