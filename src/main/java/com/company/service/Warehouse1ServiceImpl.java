package com.company.service;
import com.company.entity.Goods;
import com.company.entity.Warehouse1;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.repository.Warehouse1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Warehouse1ServiceImpl implements Warehouse1Service {

    private Warehouse1Repository warehouse1Repository;
    private GoodsService goodsService;

    @Autowired
    public Warehouse1ServiceImpl(Warehouse1Repository warehouse1Repository, GoodsService goodsService) {
        this.warehouse1Repository = warehouse1Repository;
        this.goodsService = goodsService;
    }

    @Override
    public void createInWh1(Long goodId, Long goodCount) {
        //if (isValidGoodCount(warehouse1.getGoodCount())) {
        //    goodsService.readGood(warehouse1.getGoodId().getId());
        //    warehouse1Repository.save(warehouse1);
        //} else {
        //    throw new InvalidArgumentException("Invalid arguments in creation in Warehouse1!");
        //}
        Goods good = goodsService.readGood(goodId);
        warehouse1Repository.save(new Warehouse1(good, goodCount));

    }
    @Override
    public Warehouse1 readInWh1(Long id) {
        if (warehouse1Repository.findById(id).isPresent()) {
            return warehouse1Repository.findById(id).get();
        } else {
            throw new NonexistentElementException("Invalid argument in reading in Warehouse1");
        }
    }

    @Override
    public List<Warehouse1> readAllInWh1() {
        return warehouse1Repository.findAll();
    }

    @Override
    public void updateInWh1(Long id, Long goodCount) {
        if (warehouse1Repository.findById(id).isPresent()) {
            warehouse1Repository.changeInWarehouse1GoodCountById(id, goodCount);
        } else {
            throw new InvalidArgumentException("Invalid argument, error in updating in Warehouse1");
        }
    }

    @Override
    public void deleteInWh1(Long id) {
        if (warehouse1Repository.findById(id).isPresent()) {
            warehouse1Repository.deleteWarehouse1ById(id);
        } else {
            throw new NonexistentElementException("Invalid argument in deleting in Warehouse1");
        }
    }

    @Override
    public void deleteAllInWh1() {
        warehouse1Repository.deleteAll();
    }

    @Override
    public void deleteEmptyInWh1() {
        warehouse1Repository.cleanWarehouse1();
    }

    @Override
    public List<Warehouse1> getInWh1OfGood(Long goodId) {
        return (warehouse1Repository.findWarehouse1ByGoodId(goodsService.readGood(goodId)));
    }
}
