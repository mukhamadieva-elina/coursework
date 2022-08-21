package com.company.controller;

import com.company.entity.Warehouse2;
import com.company.exception.InvalidArgumentException;
import com.company.exception.NonexistentElementException;
import com.company.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/company/warehouses/data")
public class Warehouse2Controller {

    Warehouse2Service warehouse2Service;

    @GetMapping("/warehouse2")
    public ResponseEntity<List<Warehouse2>> getAll() {
        List<Warehouse2> warehouse2List = warehouse2Service.readAllInWh2();
        return new ResponseEntity<>(warehouse2List, HttpStatus.OK);
    }

    @GetMapping("/warehouse2/{id}")
    public ResponseEntity<Warehouse2> get(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(warehouse2Service.readInWh2(id), HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse2 with this ID not found");
        }
    }

    @PutMapping("/warehouse2/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestParam long goodCount) {
        try {
            warehouse2Service.updateInWh2(id, goodCount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse2 with this ID not found");
        }
    }

    @DeleteMapping("/warehouse2/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            warehouse2Service.deleteInWh2(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonexistentElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position in Warehouse2 with this ID not found");
        }
    }

    @DeleteMapping("/warehouse2")
    public ResponseEntity<?> deleteAll() {
        warehouse2Service.deleteAllInWh2();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/warehouse2/clean")
    public ResponseEntity<?> deleteAllEmpty() {
        warehouse2Service.deleteEmptyInWh2();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/warehouse2/create")
    public ResponseEntity<?> postInWarehouse2(@RequestBody Warehouse2 warehouse2) {
        try {
            warehouse2Service.createInWh2(warehouse2.getGoodId().getId(), warehouse2.getGoodCount());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid arguments");
        }
    }

    @Autowired
    public void setWarehouse2Service(Warehouse2Service warehouse2Service) {
        this.warehouse2Service = warehouse2Service;
    }

}
