package org.bezrukov.dao;

import org.bezrukov.configuration.DBConnection;
import org.bezrukov.entity.Author;
import org.bezrukov.entity.Book;
import org.bezrukov.model.BookShortModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookDAO {
    public BookShortModel add(Book book, List<Author> authors) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(book);
        book.setAuthors(authors);
        transaction.commit();
        session.close();
        return new BookShortModel(book.getName());
    }

    public boolean remove(Integer id) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        Book book = (Book) session.get(Book.class, id);
        if (book != null) {
            session.remove(book);
            transaction.commit();
        }
        session.close();
        if(book == null) {
            return false;
        }
        return true;
    }
}
