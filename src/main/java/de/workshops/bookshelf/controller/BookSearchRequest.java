package de.workshops.bookshelf.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookSearchRequest {

    @Pattern(regexp = "\\d{3}-\\d+")
    private String isbn;
    @NotBlank
    private String author;


    public String getIsbn() {
        return isbn;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
