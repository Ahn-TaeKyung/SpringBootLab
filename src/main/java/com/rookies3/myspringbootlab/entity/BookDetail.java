package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column
    private String description;

    @Column
    private String language;

    @Column
    private String publisher;

    @Column
    private String coverImageUrl;

    @Column
    private String edition;

    @Column
    private Integer pageCount;

    //1:1 지연로딩
    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn은 FK(외래키)에 해당하는 어노테이션
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}
