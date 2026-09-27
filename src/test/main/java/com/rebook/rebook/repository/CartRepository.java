package com.rebook.rebook.repository;


import com.rebook.rebook.entity.Cart;
import com.rebook.rebook.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;



public interface CartRepository extends JpaRepository<Cart, Long> {


    List<Cart> findByUser(User user);
    void deleteByBookId(Long bookId);


}