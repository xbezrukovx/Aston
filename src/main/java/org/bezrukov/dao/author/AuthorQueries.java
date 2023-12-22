package org.bezrukov.dao.author;

public class AuthorQueries {
    public static final String GET_BY_ID = "select * from authors where id = ?";
    public static final String SAVE = "update authors set (name = ?) where id = ?";
    public static final String ADD = "insert into authors (name) values (?)";
    public static final String REMOVE = "delete authors where id = ?";
    public static final String DDL = "create table if not exists authors (id SERIAL, name text, PRIMARY KEY(id))";
    public static final String GET_ALL = "select * from authors";
}
