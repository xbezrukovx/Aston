package org.bezrukov.model;

import java.util.List;

public class AuthorModel {
    private String name;
    private List<BookShortModel> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookShortModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookShortModel> books) {
        this.books = books;
    }
}
