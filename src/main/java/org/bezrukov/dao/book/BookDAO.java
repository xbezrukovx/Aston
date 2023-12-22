package org.bezrukov.dao.book;

import org.bezrukov.configuration.DBConnection;
import org.bezrukov.dao.DataObject;
import org.bezrukov.model.Book;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookDAO implements DataObject<Book> {
    private static boolean isInit = false;

    public BookDAO () {
        if (!isInit) {
            init();
            isInit = true;
        }
    }

    private static void init() {
        Connection con = DBConnection.getConnection();
        try {
            con.createStatement().executeQuery(BookQueries.DDL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book save(Book entity) {
        Connection con = DBConnection.getConnection();
        Integer id = entity.getId();
        PreparedStatement statement;
        if (id == null) {
            try {
                statement = con.prepareStatement(BookQueries.ADD, new String[]{"id"});
                statement.setString(1, entity.getName());
                statement.executeUpdate();
                ResultSet response = statement.getGeneratedKeys();
                if(response.next()) {
                    Integer autoId = response.getInt(1);
                    entity.setId(autoId);
                    return entity;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                statement = con.prepareStatement(BookQueries.SAVE);
                statement.setString(1, entity.getName());
                statement.setInt(2 , entity.getId());
                statement.executeUpdate();
                return entity;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(BookQueries.GET_ALL);
            ResultSet response = statement.executeQuery();
            while (response.next()) {
                Integer id = response.getInt("id");
                String name = response.getString("name");
                Book book = new Book();
                book.setName(name);
                book.setId(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book getByID(Integer id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(BookQueries.GET_BY_ID);
            statement.setInt(1, id);
            ResultSet response = statement.executeQuery();
            if (response.next()) {
                Integer bookId = response.getInt("id");
                String bookName = response.getString("name");
                Book book = new Book();
                book.setId(bookId);
                book.setName(bookName);
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean remove(Book entity) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(BookQueries.REMOVE, new String[]{"id"});
            statement.setString(1, entity.getName());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
