
package com.rebook.rebook.security;


import com.rebook.rebook.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;



public class CustomUserDetails implements UserDetails {


    private final User user;



    public CustomUserDetails(User user) {

        this.user = user;

    }



    public User getUser() {

        return user;

    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return java.util.List.of(
                () -> "ROLE_" + user.getRole().name()
        );

    }




    @Override
    public String getPassword() {

        return user.getPassword();

    }




    @Override
    public String getUsername() {

        return user.getEmail();

    }




    @Override
    public boolean isEnabled() {

        return user.isEnabled();

    }


}