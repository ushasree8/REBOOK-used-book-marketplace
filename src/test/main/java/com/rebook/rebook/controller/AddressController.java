package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.security.CustomUserDetails;
import com.rebook.rebook.service.AddressService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyer")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public String addressPage(Model model) {

        model.addAttribute("address", new Address());

        return "buyer/address";
    }

    @PostMapping("/save-address")
    public String saveAddress(
            @ModelAttribute Address address,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        address.setUser(user);

        addressService.save(address);
return "redirect:/checkout";
    }
}