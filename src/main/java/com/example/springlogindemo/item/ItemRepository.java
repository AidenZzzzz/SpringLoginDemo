package com.example.springlogindemo.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author aiden
 */
@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{


    /**
     * find all item with the name itemName
     * @param itemName name of the product
     * @return iterable object of matching result
     */
    Optional<Item> findByItemName(String itemName);

    // TODO: 6/22/22 insert item query

//    @Transactional
//    @Modifying
//    int addItem(Item item);

}
