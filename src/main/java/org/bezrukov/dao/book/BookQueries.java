package org.bezrukov.dao.book;

public class BookQueries {
    public static final String GET_BY_ID = "select * from books where id = ?";
    public static final String SAVE = "update books set name = ? where id = ?";
    public static final String ADD = "insert into books (name) values (?)";
    public static final String REMOVE = "delete books where id = ?";
    public static final String GET_ALL = "select * from books";
    public static final String DDL = "create table if not exists books (id SERIAL, name text, PRIMARY KEY(id))";

}
