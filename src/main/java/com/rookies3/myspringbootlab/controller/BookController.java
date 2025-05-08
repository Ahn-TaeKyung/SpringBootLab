package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        ResponseEntity<Book> responseEntity = optionalBook.map(book -> ResponseEntity.ok(book)).orElse(new ResponseEntity("Book Not Found", HttpStatus.NOT_FOUND));
        return responseEntity;
    }

    @GetMapping("/isbn/{isbn}/")
    public Book getBookByIsbn(@PathVariable String isbn){
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        Book existBook = optionalBook.orElseThrow(()->new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return existBook;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
        Book existBook = bookRepository.findById(id).orElseThrow(()->new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        existBook.setPrice(bookDetail.getPrice());
        Book updatedBook = bookRepository.save(existBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Book book = bookRepository.findById(id).orElseThrow(()->new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
        return ResponseEntity.ok("Book Deleted");
    }
}
