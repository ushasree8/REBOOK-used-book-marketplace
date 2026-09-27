package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Book;
import com.rebook.rebook.service.BookService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final BookService bookService;

    public BuyerController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String buyerHome(
            @RequestParam(required = false) String keyword,
            Model model
    ) {

        if (keyword != null && !keyword.isBlank()) {

            model.addAttribute(
                    "books",
                    bookService.searchBooks(keyword)
            );

        } else {

            model.addAttribute(
                    "books",
                    bookService.getAllBooks()
            );

        }

        return "buyer/home";
    }

    @GetMapping("/book/{id}")
    public String bookDetails(
            @PathVariable Long id,
            Model model
    ) {

        Book book = bookService.getBookById(id);

        model.addAttribute(
                "book",
                book
        );

        return "buyer/book-details";
    }

}