package org.bezrukov.dto;

public class BookShortDTO {
    private String name;

    public BookShortDTO() {
    }

    public BookShortDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
