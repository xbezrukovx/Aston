package org.bezrukov.dao.booknauthor;

import org.bezrukov.configuration.DBConnection;
import org.bezrukov.dao.DataObject;
import org.bezrukov.dao.book.BookQueries;
import org.bezrukov.dto.BookNAuthor;
import org.bezrukov.model.Book;
import org.bezrukov.model.BooksNAuthors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksNAuthorsDAO {
    private static boolean isInit = false;

    public BooksNAuthorsDAO () {
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

    public BooksNAuthors save(BooksNAuthors entity) {
        Connection con = DBConnection.getConnection();
        Integer id = entity.getId();
        if (id == null) {
            try {
                PreparedStatement statement = con.prepareStatement(BooksNAuthorsQueries.ADD, new String[]{"id"});
                statement.setInt(1, entity.getAuthorId());
                statement.setInt(2, entity.getBookId());
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
            throw new UnsupportedOperationException();
        }

        return null;
    }

    public List<BookNAuthor> getAll() {
        List<BookNAuthor> booksNAuthors = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(BooksNAuthorsQueries.GET);
            ResultSet response = statement.executeQuery();
            while (response.next()) {
                String author = response.getString("author");
                String book = response.getString("book");
                BookNAuthor bookNAuthor = new BookNAuthor();
                bookNAuthor.setAuthor(author);
                bookNAuthor.setBook(book);
                booksNAuthors.add(bookNAuthor);
            }
            return booksNAuthors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
