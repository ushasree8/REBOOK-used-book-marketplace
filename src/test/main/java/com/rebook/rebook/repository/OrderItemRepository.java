
package com.rebook.rebook.repository;


import com.rebook.rebook.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderItemRepository 
        extends JpaRepository<OrderItem,Long>{


    void deleteByBookId(Long bookId);


}