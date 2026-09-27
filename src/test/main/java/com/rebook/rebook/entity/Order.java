package com.rebook.rebook.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;


    private BigDecimal totalAmount;


    private String status = "PENDING";


    private LocalDateTime createdAt = LocalDateTime.now();


    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderItem> items;



    public Order(){
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id=id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user=user;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address=address;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount=totalAmount;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status=status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt=createdAt;
    }


    public List<OrderItem> getItems() {
        return items;
    }


    public void setItems(List<OrderItem> items) {
        this.items=items;
    }

}