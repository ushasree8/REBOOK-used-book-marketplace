package com.rebook.rebook.controller;

import com.rebook.rebook.security.CustomUserDetails;
import com.rebook.rebook.service.BookService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final BookService bookService;

    public SellerController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/dashboard")
    public String sellerDashboard(Model model, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long sellerId = userDetails.getUser().getId();

        int totalBooks = bookService.getBooksBySeller(sellerId).size();

        model.addAttribute("totalBooks", totalBooks);

        return "seller/dashboard";
    }

}