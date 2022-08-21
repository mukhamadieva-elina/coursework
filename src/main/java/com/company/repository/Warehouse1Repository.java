package com.company.repository;

import com.company.entity.Goods;
import com.company.entity.Warehouse1;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface Warehouse1Repository extends CrudRepository<Warehouse1, Long> {

    @Transactional
    @Modifying
    void deleteWarehouse1ById(Long id);
    List <Warehouse1> findAll();

    @Transactional
    @Modifying
    @Query("delete from WAREHOUSE1 w1 where w1.goodCount = 0")
    void cleanWarehouse1();

    @Transactional
    @Modifying
    @Query("update WAREHOUSE1 w1 set w1.goodCount = ?2 where w1.id = ?1")
    void changeInWarehouse1GoodCountById(Long id, Long goodCount);

    List<Warehouse1> findWarehouse1ByGoodId(Goods good);
}
