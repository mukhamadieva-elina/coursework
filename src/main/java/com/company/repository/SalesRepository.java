package com.company.repository;

import com.company.entity.Goods;
import com.company.entity.Sales;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface SalesRepository extends CrudRepository<Sales, Long> {

    List<Sales> findSalesByGoodId(Goods good);
    List<Sales> findAll();

    @Transactional
    @Modifying
    void deleteSalesById(Long id);

    @Transactional
    @Modifying
    @Query("update SALES s set s.goodCount = ?2, s.createDate = ?3 where s.id = ?1")
    void changeSale(Long id, Long goodCount, Timestamp date);

}
