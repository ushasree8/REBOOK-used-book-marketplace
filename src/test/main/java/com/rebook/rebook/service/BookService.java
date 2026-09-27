package com.rebook.rebook.service;

import com.rebook.rebook.entity.Book;
import com.rebook.rebook.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksBySeller(Long sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    public List<Book> searchBooks(String keyword) {

        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

  

}