
package com.rebook.rebook.service;


import com.rebook.rebook.entity.Cart;
import com.rebook.rebook.entity.Book;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.repository.CartRepository;
import com.rebook.rebook.repository.BookRepository;


import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class CartService {


    private final CartRepository cartRepository;

    private final BookRepository bookRepository;



    public CartService(
            CartRepository cartRepository,
            BookRepository bookRepository
    ) {

        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;

    }




    public void addToCart(Long bookId, User user) {


        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found")
                );



        Cart cart = new Cart();


        cart.setBook(book);

        cart.setUser(user);

        cart.setQuantity(1);



        cartRepository.save(cart);

    }




    public List<Cart> getUserCart(User user) {


        return cartRepository.findByUser(user);

    }




    public void removeCartItem(Long id) {


        cartRepository.deleteById(id);

    }


}