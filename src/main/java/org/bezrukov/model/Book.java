package org.bezrukov.model;

import java.util.UUID;

public class Book implements Entity {
    private Integer id;
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
