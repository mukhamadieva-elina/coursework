package com.company.repository;

import com.company.entity.Goods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends CrudRepository<Goods, Long> {

    List<Goods> findGoodsByPriorityGreaterThan(Double priority);
    List<Goods> findAll();
    Optional<Goods> findGoodsById(Long id);

    @Transactional
    @Modifying
    void deleteGoodsById(Long id);

    @Query("select g from GOODS g where g.name like concat(?1, '%')")
    List<Goods> findGoodsByNameStartingWith(String prefix);

    @Query("select g from GOODS g order by g.priority desc")
    List<Goods> orderGoodsByPriorityDesc();

    @Query("select g from GOODS g order by g.name asc")
    List<Goods> orderGoodsByNameAsc();

    @Query("select g from GOODS g inner join SALES s on g.id = s.goodId.id where s.createDate > ?1")
    List<Goods> findGoodsWithDateOfSaleAfter(Timestamp date);

    @Query("select g from GOODS g inner join SALES s on g.id = s.goodId.id where s.createDate > ?1 and s.createDate < ?2 order by g.id asc")
    List<Goods> findGoodsWithDateOfSaleBetween(Timestamp date1, Timestamp date2);

    @Query("select distinct g from GOODS g inner join WAREHOUSE1 w1 on g.id = w1.goodId.id where w1.goodCount > 0 order by g.id asc")
    List<Goods> findGoodsInWarehouse1();

    @Query("select distinct g from GOODS g inner join WAREHOUSE2 w2 on g.id = w2.goodId.id where w2.goodCount > 0 order by g.id asc")
    List<Goods> findGoodsInWarehouse2();

    @Query("select distinct g from GOODS g left join WAREHOUSE1 w1 on g.id = w1.goodId.id left join WAREHOUSE2 w2 on g.id = w2.goodId.id where w1.goodCount > 0 or w2.goodCount > 0")
    List<Goods> findGoodsInWarehouses();

    @Transactional
    @Modifying
    @Query("update GOODS g set g.name = ?2 where g.id = ?1")
    void changeNameOfGood(Long id, String name);

    @Transactional
    @Modifying
    @Query("update GOODS g set g.priority = ?2 where g.id = ?1")
    void changePriorityOfGood(Long id, Double priority);

}
