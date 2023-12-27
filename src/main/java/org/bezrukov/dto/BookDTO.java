package org.bezrukov.dto;

import java.util.List;

public class BookDTO {
    private String name;
    private List<AuthorShortDTO> author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorShortDTO> getAuthor() {
        return author;
    }

    public void setAuthor(List<AuthorShortDTO> author) {
        this.author = author;
    }
}
