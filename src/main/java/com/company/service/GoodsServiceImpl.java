package com.company.service;

import com.company.entity.Goods;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.repository.GoodsRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private GoodsRepository goodsRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public void createGood(String name, Double priority) {
        Goods good = new Goods(name, priority);
        goodsRepository.save(good);
    }

    @Override
    public Goods readGood(Long id) {
        if (goodsRepository.findGoodsById(id).isPresent()) {
            return goodsRepository.findGoodsById(id).get();
        } else {
            throw new NonexistentElementException("Invalid argument, Good with this id doesn't exist!");
        }
    }

    @Override
    public List<Goods> readGoodsStartWith(String name) {
        return goodsRepository.findGoodsByNameStartingWith(name);
    }

    @Override
    public List<Goods> readAllGoods() {
        return goodsRepository.findAll();
    }

    @Override
    public void updateGoodsName(Long id, String name) {
        if (goodsRepository.findGoodsById(id).isPresent()) {
            goodsRepository.changeNameOfGood(id, name);
        } else {
            throw new InvalidArgumentException("Invalid argument, error in updating Good (name)");
        }
    }

    @Override
    public void updateGoodsPriority(Long id, Double priority) {
        if (goodsRepository.findGoodsById(id).isPresent()) {
            goodsRepository.changePriorityOfGood(id, priority);
        } else {
            throw new InvalidArgumentException("Invalid argument, error in updating Good (priority)");
        }
    }

    @Override
    public void deleteGood(Long id) {
        if (goodsRepository.findGoodsById(id).isPresent()) {
            goodsRepository.deleteGoodsById(id);
        } else {
            throw new NonexistentElementException("Invalid argument, Good with this id doesn't exist!");
        }
    }

    @Override
    public void deleteAllGoods() {
        goodsRepository.deleteAll();
    }

    @Override
    public List<Goods> orderGoodsBy(String sortingAttribute) {
        if (sortingAttribute.equals("name")) {
            return goodsRepository.orderGoodsByNameAsc();
        } else if (sortingAttribute.equals("priority")) {
            return goodsRepository.orderGoodsByPriorityDesc();
        } else {
            throw new InvalidArgumentException("Invalid argument in method of ordering Goods");
        }
    }

    @Override
    public List<Goods> getHighPriorityGoods() {
        double high = 5.0;
        return goodsRepository.findGoodsByPriorityGreaterThan(high);
    }

    @Override
    public List<Goods> getGoodsWithSalesAfterDate(Timestamp date) {
        return goodsRepository.findGoodsWithDateOfSaleAfter(date);
    }

    @Override
    public List<Goods> getGoodsWithSalesInPeriod(Timestamp date1, Timestamp date2) {
        return goodsRepository.findGoodsWithDateOfSaleBetween(date1, date2);
    }

}
