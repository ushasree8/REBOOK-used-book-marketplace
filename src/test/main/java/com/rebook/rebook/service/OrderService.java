package com.rebook.rebook.service;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.Cart;
import com.rebook.rebook.entity.Order;
import com.rebook.rebook.entity.OrderItem;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.repository.CartRepository;
import com.rebook.rebook.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            CartRepository cartRepository,
            OrderRepository orderRepository
    ) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(User user, Address address) {

        List<Cart> cartItems = cartRepository.findByUser(user);

        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (Cart cart : cartItems) {

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setBook(cart.getBook());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getBook().getPrice());

            total = total.add(
                    cart.getBook()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(cart.getQuantity()))
            );

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

}