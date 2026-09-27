package com.rebook.rebook.controller;


import com.rebook.rebook.entity.Book;
import com.rebook.rebook.entity.User;

import com.rebook.rebook.security.CustomUserDetails;

import com.rebook.rebook.service.BookService;


import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.*;



@Controller
@RequestMapping("/seller")
public class BookController {



    private final BookService bookService;



    public BookController(BookService bookService) {

        this.bookService = bookService;

    }





    @GetMapping("/add-book")
    public String addBookPage(Model model) {


        model.addAttribute(
                "book",
                new Book()
        );


        return "seller/add-book";

    }






    @PostMapping("/save-book")
    public String saveBook(
            @ModelAttribute Book book,
            @RequestParam("imageFile") MultipartFile imageFile,
            Authentication authentication
    ) throws IOException {



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        User seller =
                userDetails.getUser();



        book.setSeller(seller);





        if(!imageFile.isEmpty()){


            String fileName =
                    imageFile.getOriginalFilename();



            Path uploadPath =
                    Paths.get("uploads/books");



            if(!Files.exists(uploadPath)){

                Files.createDirectories(uploadPath);

            }



            Files.copy(
                    imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );



            book.setImageName(fileName);

        }




        bookService.saveBook(book);



        return "redirect:/seller/books";

    }








    @GetMapping("/books")
    public String viewBooks(
            Model model,
            Authentication authentication
    ) {



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        Long sellerId =
                userDetails.getUser().getId();



        model.addAttribute(
                "books",
                bookService.getBooksBySeller(sellerId)
        );



        return "seller/books";

    }









    @GetMapping("/edit/{id}")
    public String editBook(
            @PathVariable Long id,
            Model model
    ){


        model.addAttribute(
                "book",
                bookService.getBookById(id)
        );


        return "seller/edit-book";

    }








    @PostMapping("/update")
    public String updateBook(
            @ModelAttribute Book book,
            Authentication authentication
    ){



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        User seller =
                userDetails.getUser();



        book.setSeller(seller);



        bookService.saveBook(book);



        return "redirect:/seller/books";

    }








    @GetMapping("/delete/{id}")
    public String deleteBook(
            @PathVariable Long id
    ){


        bookService.deleteBook(id);



        return "redirect:/seller/books";

    }



}