package com.company.controller;

import com.company.entity.Warehouse1;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.service.Warehouse1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/company/warehouses/data")
public class Warehouse1Controller {

    Warehouse1Service warehouse1Service;

    @GetMapping("/warehouse1")
    public ResponseEntity<List<Warehouse1>> getAll() {
        List<Warehouse1> warehouse2List = warehouse1Service.readAllInWh1();
        return new ResponseEntity<>(warehouse2List, HttpStatus.OK);
    }

    @GetMapping("/warehouse1/{id}")
    public ResponseEntity<Warehouse1> get(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(warehouse1Service.readInWh1(id), HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse1 with this ID not found");
        }
    }

    @PutMapping("/warehouse1/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestParam long goodCount) {
        try {
            warehouse1Service.updateInWh1(id, goodCount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse1 with this ID not found");
        }
    }

    @DeleteMapping("/warehouse1/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            warehouse1Service.deleteInWh1(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse1 with this ID not found");
        }
    }

    @DeleteMapping("/warehouse1")
    public ResponseEntity<?> deleteAll() {
        warehouse1Service.deleteAllInWh1();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/warehouse1/clean")
    public ResponseEntity<?> deleteAllEmpty() {
        warehouse1Service.deleteEmptyInWh1();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/warehouse1/create")
    public ResponseEntity<?> postInWarehouse1(@RequestBody Warehouse1 warehouse1) {
        try {
            warehouse1Service.createInWh1(warehouse1.getGoodCount(), warehouse1.getGoodCount());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid arguments");
        }
    }
    @Autowired
    public void setWarehouse1Service(Warehouse1Service warehouse1Service) {
        this.warehouse1Service = warehouse1Service;
    }

}
