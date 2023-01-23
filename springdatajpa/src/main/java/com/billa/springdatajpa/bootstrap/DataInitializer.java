package com.billa.springdatajpa.bootstrap;

import com.billa.springdatajpa.domaine.Book;
import com.billa.springdatajpa.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book("12456","life of pie", "praveen");

        System.out.println(book.getId()+" before save");

        book = bookRepository.save(book);
        System.out.println(book.getId()+" before save");
        Book book1 = new Book("12456","life of pie", "praveen");

        bookRepository.save(book1);

        System.out.println("find all books");
        bookRepository.findAll().stream().forEach(System.out::println);

    }
}
