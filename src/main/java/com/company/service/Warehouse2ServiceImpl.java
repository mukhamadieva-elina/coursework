package com.company.service;

import com.company.entity.Goods;
import com.company.entity.Warehouse2;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.repository.Warehouse2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Warehouse2ServiceImpl implements Warehouse2Service {

    private Warehouse2Repository warehouse2Repository;
    private GoodsService goodsService;

    @Autowired
    public Warehouse2ServiceImpl(Warehouse2Repository warehouse2Repository, GoodsService goodsService) {
        this.warehouse2Repository = warehouse2Repository;
        this.goodsService = goodsService;
    }

    @Override
    public void createInWh2(Long goodId, Long goodCount) {
        Goods good = goodsService.readGood(goodId);
        warehouse2Repository.save(new Warehouse2(good, goodCount));
    }

    @Override
    public Warehouse2 readInWh2(Long id) {
        if (warehouse2Repository.findById(id).isPresent()) {
            return warehouse2Repository.findById(id).get();
        } else {
            throw new NonexistentElementException("Invalid argument in reading in Warehouse2");
        }
    }

    @Override
    public List<Warehouse2> readAllInWh2() {
        return warehouse2Repository.findAll();
    }

    @Override
    public void updateInWh2(Long id, Long goodCount) {
        if (warehouse2Repository.findById(id).isPresent()) {
            warehouse2Repository.changeInWarehouse2GoodCountById(id, goodCount);
        } else {
            throw new InvalidArgumentException("Invalid argument, error in updating in Warehouse2");
        }
    }

    @Override
    public void deleteInWh2(Long id) {
        if (warehouse2Repository.findById(id).isPresent()) {
            warehouse2Repository.deleteWarehouse2ById(id);
        } else {
            throw new NonexistentElementException("Invalid argument in deleting in Warehouse2");
        }
    }

    @Override
    public void deleteAllInWh2() {
        warehouse2Repository.deleteAll();
    }

    @Override
    public void deleteEmptyInWh2() {
        warehouse2Repository.cleanWarehouse2();
    }

    @Override
    public List<Warehouse2> getInWh2OfGood(Long goodId) {
        return (warehouse2Repository.findWarehouse2ByGoodId(goodsService.readGood(goodId)));
    }
}
