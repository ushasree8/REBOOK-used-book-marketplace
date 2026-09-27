package com.rebook.rebook.config;


import com.rebook.rebook.security.CustomAuthenticationSuccessHandler;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {



    private final CustomAuthenticationSuccessHandler successHandler;



    public SecurityConfig(
            CustomAuthenticationSuccessHandler successHandler
    ){

        this.successHandler = successHandler;

    }





    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }





    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        http



        .authorizeHttpRequests(auth -> auth



                .requestMatchers(
                        "/login",
                        "/register",
                        "/books/**",
                        "/images/**",
                        "/css/**"
                )
                .permitAll()



                .requestMatchers("/admin/**")
                .hasRole("ADMIN")



                .requestMatchers("/seller/**")
                .hasRole("SELLER")



                .requestMatchers("/buyer/**")
                .hasRole("BUYER")



                .anyRequest()
                .authenticated()

        )





        .formLogin(login -> login



                .loginPage("/login")



                .loginProcessingUrl("/login")



                .successHandler(successHandler)



                .permitAll()

        )





        .logout(logout -> logout



                .logoutUrl("/logout")



                .logoutSuccessUrl("/login?logout")



                .invalidateHttpSession(true)



                .clearAuthentication(true)



                .deleteCookies("JSESSIONID")



                .permitAll()

        );





        return http.build();

    }


}