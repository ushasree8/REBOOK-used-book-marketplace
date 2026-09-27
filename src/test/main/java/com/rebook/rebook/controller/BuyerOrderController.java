package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Order;
import com.rebook.rebook.repository.OrderRepository;
import com.rebook.rebook.security.CustomUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/buyer")
public class BuyerOrderController {

    private final OrderRepository orderRepository;

    public BuyerOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public String myOrders(
            Authentication authentication,
            Model model
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId =
                userDetails.getUser().getId();

        List<Order> orders =
                orderRepository.findByUserId(userId);

        model.addAttribute(
                "orders",
                orders
        );

        return "buyer/orders";
    }

}