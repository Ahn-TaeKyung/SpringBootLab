package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//읽기전용모드 (성능 최적화)
public class BookService {
    private final BookRepository bookRepository;

    //등록
    @Transactional
    public BookDTO.BookResponse createUser(BookDTO.BookCreateRequest request) {
        //Email 중복검사
        bookRepository.findByIsbn(request.getIsbn()) //Optional<User>
                .ifPresent(
                        user -> {
                            throw new BusinessException("Book with this Isbn already Exist",HttpStatus.CONFLICT);
                        });
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return new BookDTO.BookResponse(savedBook);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //수정
    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest bookDetail) {
        Book book = getBookById(id);
        //dirty read
        book.setTitle(bookDetail.getTitle());
        book.setPrice(bookDetail.getPrice());
        book.setAuthor(bookDetail.getAuthor());
        book.setPublishDate(bookDetail.getPublishDate());
        //return userRepository.save(user);
        return new BookDTO.BookResponse(book);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    //삭제
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

}