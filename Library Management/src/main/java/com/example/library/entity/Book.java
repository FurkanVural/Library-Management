package com.example.library.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "BOOK")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="BOOKNAME")
    private String bookname;
    @Column(name="AUTHOR")
    private String author;
    @Column(name="GENRE")
    private String genre;
    @Column(name = "PUBLISH_DATE")
    private LocalDateTime year;

}
