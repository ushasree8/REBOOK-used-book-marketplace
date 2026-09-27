package com.rebook.rebook.controller;


import com.rebook.rebook.entity.Order;
import com.rebook.rebook.repository.OrderRepository;
import com.rebook.rebook.security.CustomUserDetails;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping("/seller")
public class SellerOrderController {



    private final OrderRepository orderRepository;



    public SellerOrderController(
            OrderRepository orderRepository
    ){

        this.orderRepository = orderRepository;

    }





    @GetMapping("/orders")
    public String sellerOrders(
            Authentication authentication,
            Model model
    ){



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        Long sellerId =
                userDetails.getUser().getId();



        List<Order> orders =
                orderRepository.findOrdersBySellerId(sellerId);



        model.addAttribute(
                "orders",
                orders
        );



        return "seller/orders";

    }





    @GetMapping("/order/status/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ){


        Order order =
                orderRepository.findById(id)
                .orElse(null);



        if(order != null){

            order.setStatus(status);

            orderRepository.save(order);

        }



        return "redirect:/seller/orders";

    }

}