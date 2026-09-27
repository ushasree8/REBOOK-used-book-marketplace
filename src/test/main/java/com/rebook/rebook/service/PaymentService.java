package com.rebook.rebook.service;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.Book;
import com.rebook.rebook.entity.Order;
import com.rebook.rebook.entity.OrderItem;
import com.rebook.rebook.entity.Payment;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.repository.PaymentRepository;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public PaymentService(
            PaymentRepository paymentRepository,
            EmailService emailService
    ) {
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
    }

    public Payment createPayment(Order order, String method) {

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(method);
        payment.setPaymentStatus("SUCCESS");

        Payment savedPayment = paymentRepository.save(payment);

        User buyer = order.getUser();
        Address address = order.getAddress();

        for (OrderItem item : order.getItems()) {

            Book book = item.getBook();
            User seller = book.getSeller();

            String body =
                    "Hello " + seller.getFirstName() + ",\n\n" +
                    "You have received a new order.\n\n" +

                    "Book : " + book.getTitle() + "\n\n" +

                    "Buyer Name : " + buyer.getFirstName() + "\n" +
                    "Phone : " + buyer.getPhone() + "\n\n" +

                    "Delivery Address:\n" +
                    address.getHouse() + ",\n" +
                    address.getStreet() + ",\n" +
                    address.getCity() + ",\n" +
                    address.getState() + " - " + address.getPincode() + "\n\n" +

                    "Payment Method : " + method + "\n\n" +

                    "Please dispatch the book.\n\n" +

                    "Thanks,\nReBook Team";

            emailService.sendEmail(
                    seller.getEmail(),
                    "New Order Received - ReBook",
                    body
            );
        }

        return savedPayment;
    }
}