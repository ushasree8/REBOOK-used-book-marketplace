package com.rebook.rebook.controller;


import com.rebook.rebook.entity.User;
import com.rebook.rebook.security.CustomUserDetails;
import com.rebook.rebook.service.CartService;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/buyer/cart")
public class CartController {


    private final CartService cartService;



    public CartController(CartService cartService) {

        this.cartService = cartService;

    }





    @GetMapping("/add/{id}")
    public String addToCart(
            @PathVariable Long id,
            Authentication authentication
    ) {



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        User user = userDetails.getUser();



        cartService.addToCart(id, user);



        return "redirect:/buyer/cart";

    }






    @GetMapping
    public String viewCart(
            Authentication authentication,
            Model model
    ) {



        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        User user = userDetails.getUser();



        model.addAttribute(
                "cartItems",
                cartService.getUserCart(user)
        );



        return "buyer/cart";

    }






    @GetMapping("/remove/{id}")
    public String removeCart(
            @PathVariable Long id
    ) {



        cartService.removeCartItem(id);



        return "redirect:/buyer/cart";

    }


}