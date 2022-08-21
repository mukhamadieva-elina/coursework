package com.company.service;

import com.company.entity.Goods;

import java.sql.Timestamp;
import java.util.List;

public interface GoodsService {

    void createGood(String name, Double priority);
    Goods readGood(Long id);
    List<Goods> readAllGoods();
    void updateGoodsName(Long id, String name);
    void updateGoodsPriority(Long id, Double priority);
    void deleteGood(Long id);
    void deleteAllGoods();
    List<Goods> readGoodsStartWith(String name);

    List<Goods> orderGoodsBy(String sortingAttribute);
    List<Goods> getHighPriorityGoods();
    List<Goods> getGoodsWithSalesAfterDate(Timestamp date);
    List<Goods> getGoodsWithSalesInPeriod(Timestamp date1, Timestamp date2);

}
