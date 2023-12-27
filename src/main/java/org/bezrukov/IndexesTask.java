package org.bezrukov;

import org.bezrukov.configuration.DBConnection;
import org.bezrukov.dao.AuthorDAO;
import org.bezrukov.dto.AuthorShortDTO;
import org.bezrukov.dto.BookShortDTO;
import org.bezrukov.entity.Author;
import org.bezrukov.services.BookService;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class IndexesTask {
    private static BookService bookService = new BookService();
    public static void main(String[] args) throws InterruptedException {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();

        for(int i = 1_000_000_100; i < 1_000_000_200; i++) {
            Author author = new Author();
            author.setName(i+"test");
            session.persist(author);
        }

        transaction.commit();
        session.close();

        session = DBConnection.getSession();

        long start = System.currentTimeMillis();
        String hql = "FROM Author WHERE name = '10000000150test'";
        Author author = session.createQuery(hql, Author.class)
                .setCacheable(true)
                .setCacheRegion("org.bezrukov.entity.Author")
                .getSingleResult();
        System.out.println(author.getId());
        long end = System.currentTimeMillis();

        System.out.println("Время выполнения: " + (end-start) + "ms");
        session.close();

        session = DBConnection.getSession();

        start = System.currentTimeMillis();
        hql = "FROM Author WHERE name = '10000000150test'";
        author = session.createQuery(hql, Author.class)
                .setCacheable(true)
                .setCacheRegion("org.bezrukov.entity.Author")
                .getSingleResult();
        System.out.println(author.getId());
        end = System.currentTimeMillis();

        System.out.println("Время выполнения: " + (end-start) + "ms");
        session.close();

    }
}
