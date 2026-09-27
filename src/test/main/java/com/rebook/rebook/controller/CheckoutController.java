package com.rebook.rebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.Order;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.security.CustomUserDetails;
import com.rebook.rebook.service.AddressService;
import com.rebook.rebook.service.OrderService;
import com.rebook.rebook.service.PaymentService;

@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "buyer/payment";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(
            Authentication authentication,
            @RequestParam("method") String method) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        Address address = addressService.getLatestAddress(user);

        if (address == null) {
            return "redirect:/buyer/address";
        }

        // Create Order
        Order order = orderService.createOrder(user, address);

        // Save Payment
        paymentService.createPayment(order, method);

        return "redirect:/orders/success";
    }

    @GetMapping("/orders/success")
    public String orderSuccess() {
        return "buyer/order-success";
    }
}