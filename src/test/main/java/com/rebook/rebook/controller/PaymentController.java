package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.Order;
import com.rebook.rebook.repository.OrderRepository;
import com.rebook.rebook.service.EmailService;
import com.rebook.rebook.service.PaymentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyer/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;

    public PaymentController(
            PaymentService paymentService,
            OrderRepository orderRepository,
            EmailService emailService
    ) {
        this.paymentService = paymentService;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    @GetMapping("/{orderId}")
    public String paymentPage(
            @PathVariable Long orderId,
            Model model
    ) {

        model.addAttribute("orderId", orderId);

        return "buyer/payment";
    }

    @PostMapping("/{orderId}")
    public String makePayment(
            @PathVariable Long orderId,
            @RequestParam String method
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Save payment
        paymentService.createPayment(order, method);

        // Seller email
        String sellerEmail =
                order.getItems()
                        .get(0)
                        .getBook()
                        .getSeller()
                        .getEmail();

        // Delivery address
        Address address = order.getAddress();

        String message =
                "Hello Seller,\n\n" +

                "You have received a new order.\n\n" +

                "Order ID : " + order.getId() + "\n\n" +

                "Book : " +
                order.getItems().get(0).getBook().getTitle() + "\n\n" +

                "Payment Method : " + method + "\n\n" +

                "Buyer Details\n\n" +

                "Name : " + address.getFullName() + "\n" +

                "Phone : " + address.getPhone() + "\n\n" +

                "Delivery Address\n\n" +

                address.getHouse() + ", " +
                address.getStreet() + "\n" +

                address.getCity() + ", " +
                address.getState() + " - " +
                address.getPincode() + "\n\n" +

                "Please dispatch the book.\n\n" +

                "Regards,\n" +
                "ReBook Team";

        emailService.sendEmail(
                sellerEmail,
                "New Order Received - ReBook",
                message
        );

        return "buyer/payment-success";
    }
}