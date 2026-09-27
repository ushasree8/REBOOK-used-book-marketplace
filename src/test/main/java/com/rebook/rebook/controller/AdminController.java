package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Order;
import com.rebook.rebook.service.BookService;

import com.rebook.rebook.repository.UserRepository;
import com.rebook.rebook.repository.OrderRepository;
import com.rebook.rebook.repository.PaymentRepository;
import com.rebook.rebook.repository.CartRepository;
import com.rebook.rebook.repository.OrderItemRepository;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin")
public class AdminController {



    private final UserRepository userRepository;

    private final BookService bookService;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    private final CartRepository cartRepository;

    private final OrderItemRepository orderItemRepository;





    public AdminController(
            UserRepository userRepository,
            BookService bookService,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            CartRepository cartRepository,
            OrderItemRepository orderItemRepository
    ){

        this.userRepository = userRepository;

        this.bookService = bookService;

        this.orderRepository = orderRepository;

        this.paymentRepository = paymentRepository;

        this.cartRepository = cartRepository;

        this.orderItemRepository = orderItemRepository;

    }






    @GetMapping("/dashboard")
    public String dashboard(
            Model model
    ){


        model.addAttribute(
                "users",
                userRepository.findAll()
        );


        model.addAttribute(
                "books",
                bookService.getAllBooks()
        );


        model.addAttribute(
                "orders",
                orderRepository.findAll()
        );

        model.addAttribute("totalUsers", userRepository.count());

model.addAttribute("totalBooks", bookService.getAllBooks().size());

model.addAttribute("totalOrders", orderRepository.count());

model.addAttribute("totalPayments", paymentRepository.count());

        return "admin/dashboard";

    }







    // Delete User

    @GetMapping("/user/delete/{id}")
    public String deleteUser(
            @PathVariable Long id
    ){


        orderRepository
                .findByUserId(id)
                .forEach(order -> {



                    paymentRepository
                            .findByOrderId(order.getId())
                            .forEach(payment -> {


                                paymentRepository.delete(payment);


                            });



                    orderRepository.delete(order);


                });



        userRepository.deleteById(id);



        return "redirect:/admin/dashboard";

    }








    // Delete Book

    @GetMapping("/book/delete/{id}")
    public String deleteBook(
            @PathVariable Long id
    ){


        cartRepository.deleteByBookId(id);


        orderItemRepository.deleteByBookId(id);


        bookService.deleteBook(id);



        return "redirect:/admin/dashboard";

    }
    @GetMapping("/order/status/{id}")
public String updateOrderStatus(
        @PathVariable Long id,
        @RequestParam String status
){

    Order order =
            orderRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Order not found")
            );


    order.setStatus(status);


    orderRepository.save(order);



    return "redirect:/admin/dashboard";

}


}