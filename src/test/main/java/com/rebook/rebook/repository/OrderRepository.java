package com.rebook.rebook.repository;


import com.rebook.rebook.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface OrderRepository extends JpaRepository<Order,Long> {



    List<Order> findByUserId(Long userId);



    @Query("""
    SELECT DISTINCT o
    FROM Order o
    JOIN o.items i
    JOIN i.book b
    WHERE b.seller.id = :sellerId
    """)
    List<Order> findOrdersBySellerId(Long sellerId);



}