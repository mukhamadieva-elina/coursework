package com.company.service;

import com.company.entity.Warehouse2;

import java.util.List;

public interface Warehouse2Service {

    void createInWh2(Long goodId, Long goodCount);
    Warehouse2 readInWh2(Long id);
    List<Warehouse2> readAllInWh2();
    void updateInWh2(Long id, Long goodCount);
    void deleteInWh2(Long id);
    void deleteAllInWh2();
    void deleteEmptyInWh2();
    List<Warehouse2> getInWh2OfGood(Long goodId);
}
