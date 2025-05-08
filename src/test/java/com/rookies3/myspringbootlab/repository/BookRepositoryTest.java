package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Rollback(value = false)
    @Disabled
    void testCreateBook(){
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.parse("2025-05-07"));

        Book addBook = bookRepository.save(book);

        assertThat(addBook).isNotNull();
        assertThat(addBook.getTitle()).isEqualTo("스프링 부트 입문");
    }
    @Test
    @Disabled
    void testFindByIsbn(){
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746425");
        if(optionalBook.isPresent()){
            Book existbook = optionalBook.get();
            assertThat(existbook.getIsbn()).isEqualTo("9788956746425");
        }
    }
    @Test
    @Disabled
    void testFindByAuthor(){
        List<Optional<Book>> optionalBookList = bookRepository.findByAuthor("홍길동");
        for (Optional<Book> optionalBook : optionalBookList){
            if(optionalBook.isPresent()){
                Book existbook = optionalBook.get();
                assertThat(existbook.getAuthor()).isEqualTo("홍길동");
            }
        }
    }
    @Test
    @Rollback(value = false)
    @Disabled
    void testUpdateBook(){
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow(()->new RuntimeException("Book Not Found"));
        book.setPrice(50000);
        Book updateBook = bookRepository.save(book);
        assertThat(updateBook.getPrice()).isEqualTo(50000);
    }
    @Test
    @Rollback(value = false)
    @Disabled
    void testDeleteBook(){
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow(()->new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }
}
