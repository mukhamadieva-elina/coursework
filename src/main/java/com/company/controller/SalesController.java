package com.company.controller;

import com.company.entity.Sales;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/company/data")
public class SalesController {

    SalesService salesService;

    @GetMapping("/sales")
    public ResponseEntity<List<Sales>> getAll() {
        List<Sales> salesList = salesService.readAllSales();
        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @GetMapping("/sales/all/{id}")
    public ResponseEntity<List<Sales>> getSalesOfGood(@PathVariable("id") long id) {
        List<Sales> salesList = salesService.getSalesOfGood(id);
        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<Sales> get(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(salesService.readSale(id), HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale with this ID not found");
        }
    }

    @PostMapping("/sales/create")
    public ResponseEntity<?> postInSales(@RequestBody Sales sale) {
        try {
            salesService.createSale(sale.getGoodId().getId(), sale.getGoodCount(), sale.getCreateDate());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid arguments");
        }
    }

    @PutMapping("/sales/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestParam long goodCount) {
        try {
            salesService.updateSale(id, goodCount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Sales with this ID not found");
        }
    }

    @DeleteMapping("/sales")
    public ResponseEntity<?> deleteAll() {
        salesService.deleteAllSales();;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            salesService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale with this ID not found");
        }
    }

    @Autowired
    public void setSalesService(SalesService salesService) {
        this.salesService = salesService;
    }
}
