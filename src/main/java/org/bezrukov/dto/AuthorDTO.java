package org.bezrukov.dto;

import java.util.List;

public class AuthorDTO {
    private String name;
    private List<BookShortDTO> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookShortDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookShortDTO> books) {
        this.books = books;
    }
}
