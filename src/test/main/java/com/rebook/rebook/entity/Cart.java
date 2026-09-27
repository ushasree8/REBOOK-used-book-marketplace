package com.rebook.rebook.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;



    private Integer quantity = 1;


    private LocalDateTime createdAt = LocalDateTime.now();



    public Cart() {

    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }



    public Book getBook() {
        return book;
    }



    public void setBook(Book book) {
        this.book = book;
    }



    public Integer getQuantity() {
        return quantity;
    }



    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}