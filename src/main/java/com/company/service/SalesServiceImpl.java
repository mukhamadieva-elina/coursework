package com.company.service;

import com.company.entity.Goods;
import com.company.entity.Sales;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    private SalesRepository salesRepository;
    private GoodsService goodsService;

    @Autowired
    public SalesServiceImpl(SalesRepository salesRepository, GoodsService goodsService) {
        this.salesRepository = salesRepository;
        this.goodsService = goodsService;
    }

    @Override
    public void createSale(Long goodId, Long goodCount, LocalDateTime createDate) {
        Goods good = goodsService.readGood(goodId);
        salesRepository.save(new Sales(good, goodCount, createDate));
    }

    @Override
    public Sales readSale(Long id) {
        if (salesRepository.findById(id).isPresent()) {
            return salesRepository.findById(id).get();
        } else {
            throw new NonexistentElementException("Invalid argument, Sale with this id doesn't exist!");
        }
    }

    @Override
    public List<Sales> readAllSales() {
        return salesRepository.findAll();
    }

    @Override
    public void updateSale(Long id, Long goodCount) {
        if (salesRepository.findById(id).isPresent()) {
            salesRepository.changeSale(id, goodCount, new Timestamp(System.currentTimeMillis()));
        } else {
            throw new InvalidArgumentException("Invalid argument, error in updating Sale");
        }
    }

    @Override
    public void deleteSale(Long id) {
        if (salesRepository.findById(id).isPresent()) {
            salesRepository.deleteSalesById(id);
        } else {
            throw new NonexistentElementException("Invalid argument, Sale with this id doesn't exist!");
        }
    }

    @Override
    public void deleteAllSales() {
        salesRepository.deleteAll();
    }

    @Override
    public List<Sales> getSalesOfGood(Long goodId) {
        return (salesRepository.findSalesByGoodId(goodsService.readGood(goodId)));
    }
}
