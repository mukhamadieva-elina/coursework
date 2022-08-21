package com.company.service;

import com.company.entity.Sales;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {

    void createSale(Long goodId, Long goodCount, LocalDateTime createDate);
    Sales readSale(Long id);
    List<Sales> readAllSales();
    void updateSale(Long id, Long goodCount);
    void deleteSale(Long id);
    void deleteAllSales();

    List<Sales> getSalesOfGood(Long goodId);

}
