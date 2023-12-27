package org.bezrukov.dto;

public class AuthorShortDTO {
    private String name;

    public AuthorShortDTO() {
    }

    public AuthorShortDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
