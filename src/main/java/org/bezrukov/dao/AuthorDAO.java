package org.bezrukov.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.NoResultException;
import org.bezrukov.configuration.DBConnection;
import org.bezrukov.entity.Author;
import org.bezrukov.model.AuthorModel;
import org.bezrukov.model.AuthorShortModel;
import org.bezrukov.model.BookShortModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorDAO {
    public Author add(Author author) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(author);
        transaction.commit();
        session.close();
        return author;
    }

    public boolean remove(Integer id) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        Author author = (Author) session.get(Author.class, id);
        if (author != null) {
            session.remove(author);
            transaction.commit();
        }
        session.close();
        if(author == null) {
            return false;
        }
        return true;
    }

    public Author getAuthorByName(String name) {
        Session session = DBConnection.getSession();
        String hql = MessageFormat.format(
            "FROM {0} WHERE name=''{1}''",
            Author.class.getSimpleName(),
            name
        );
        Author author = null;
        try {
            author = session.createQuery(hql, Author.class).getSingleResult();
        } catch (NoResultException e) {
            author = new Author();
            author.setName(name);
            add(author);
        }
        session.close();
        return author;
    }

    public AuthorShortModel getShortInfoById(Integer id) {
        Session session = DBConnection.getSession();
        Author author = session.get(Author.class, id);
        session.close();
        /*
            Если вызвать тут получение книг, то выйдет LazyInitializationException
            author.getBooks();
            Ситуация исправлена через entityGraph в методе getAll();
         */

        return new AuthorShortModel(author.getName());
    }

    public List<AuthorModel> getAll() {
        Session session = DBConnection.getSession();
        EntityGraph<Author> entityGraph = session.createEntityGraph(Author.class);
        entityGraph.addAttributeNodes("books");
        String hql = MessageFormat.format("FROM {0}", Author.class.getSimpleName());
        List<AuthorModel> authors = session.createQuery(hql, Author.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList()
                .stream().map(a -> {
                    AuthorModel authorModel = new AuthorModel();
                    authorModel.setName(a.getName());
                    authorModel.setBooks(
                        a.getBooks()
                                .stream()
                                .map(b -> new BookShortModel(b.getName()))
                                .collect(Collectors.toList())
                    );
                    return authorModel;
                }).collect(Collectors.toList());
        session.close();
        return authors;
    }
}
