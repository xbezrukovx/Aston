package org.bezrukov.dao.author;

import org.bezrukov.configuration.DBConnection;
import org.bezrukov.dao.DataObject;
import org.bezrukov.dao.book.BookQueries;
import org.bezrukov.model.Author;
import org.bezrukov.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthorDAO implements DataObject<Author> {

    private static boolean isInit = false;

    public AuthorDAO () {
        if (!isInit) {
            init();
            isInit = true;
        }
    }

    private static void init() {
        Connection con = DBConnection.getConnection();
        try {
            con.createStatement().executeQuery(AuthorQueries.DDL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author save(Author entity) {
        Connection con = DBConnection.getConnection();
        Integer id = entity.getId();
        PreparedStatement statement;
        if (id == null) {
            try {
                statement = con.prepareStatement(AuthorQueries.ADD, new String[]{"id"});
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
                statement = con.prepareStatement(AuthorQueries.SAVE);
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
    public Author getByID(Integer id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(AuthorQueries.GET_BY_ID);
            statement.setInt(1, id);
            ResultSet response = statement.executeQuery();
            if (response.next()) {
                Integer authorId = response.getInt("id");
                String authorName = response.getString("name");
                Author author = new Author();
                author.setId(authorId);
                author.setName(authorName);
                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(AuthorQueries.GET_ALL);
            ResultSet response = statement.executeQuery();
            while (response.next()) {
                Integer id = response.getInt("id");
                String name = response.getString("name");
                Author author = new Author();
                author.setName(name);
                author.setId(id);
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(Author entity) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(AuthorQueries.REMOVE, new String[]{"id"});
            statement.setString(1, entity.getName());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
