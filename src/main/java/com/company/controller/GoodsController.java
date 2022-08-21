package com.company.controller;

import com.company.entity.Goods;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/company/data")
public class GoodsController {

    GoodsService goodsService;

    @GetMapping("/goods")
    public ResponseEntity<List<Goods>> getAll() {
        List<Goods> goodsList = goodsService.readAllGoods();
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("/goods/{id}")
    public ResponseEntity<Goods> get(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(goodsService.readGood(id), HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good with this ID not found");
        }
    }

    @GetMapping("/goods/order")
    public ResponseEntity<List<Goods>> getInOrder(@RequestParam String attribute) {
        try {
            List<Goods> goodsList = goodsService.orderGoodsBy(attribute);
            return new ResponseEntity<>(goodsList, HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good with this ID not found");
        }
    }

    @GetMapping("/goods/high")
    public ResponseEntity<List<Goods>> getHighPriority() {
        List<Goods> goodsList = goodsService.getHighPriorityGoods();
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("/goods/after")
    public ResponseEntity<List<Goods>> getGoodsAfter(@RequestParam Timestamp date) {
        List<Goods> goodsList = goodsService.getGoodsWithSalesAfterDate(date);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("/goods/between")
    public ResponseEntity<List<Goods>> getGoodsAfter(@RequestParam Timestamp date1, @RequestParam Timestamp date2) {
        List<Goods> goodsList = goodsService.getGoodsWithSalesInPeriod(date1, date2);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @PostMapping("/goods/create")
    public ResponseEntity<?> postInGoods(@RequestBody Goods good) {
        try {
            goodsService.createGood(good.getName(), good.getPriority());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid arguments");
        }
    }

    @PutMapping("/goods/name/{id}")
    @ResponseBody
    public ResponseEntity<?> updateName(@PathVariable("id") long id, @RequestParam String name) {
        try {
            goodsService.updateGoodsName(id, name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Goods with this ID not found");
        }
    }

    @PutMapping("/goods/priority/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePriority(@PathVariable("id") long id, @RequestParam double priority) {
        try {
            goodsService.updateGoodsPriority(id, priority);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Goods with this ID not found");
        }
    }

    @DeleteMapping("/goods")
    public ResponseEntity<?> deleteAll() {
        goodsService.deleteAllGoods();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/goods/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            goodsService.deleteGood(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good with this ID not found");
        }
    }

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
}
