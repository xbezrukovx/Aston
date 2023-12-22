package org.bezrukov.dao.booknauthor;

public class BooksNAuthorsQueries {
    public static final String DDL = "create table if not exists books_authors \n" +
            "(id SERIAL, author_id integer, book_id integer,\n" +
            "CONSTRAINT fk_author FOREIGN KEY(author_id) REFERENCES authors(id),\n" +
            "CONSTRAINT fk_book FOREIGN KEY(book_id) REFERENCES books(id)\n" +
            ")";
    public static final String ADD = "insert into books_authors (author_id, book_id) values (?, ?)";
    public static final String GET = "select \n" +
            "\ta.name as book,\n" +
            "\tb.name as author\n" +
            "from books_authors c\n" +
            "join authors b on c.author_id = b.id\n" +
            "join books a on c.book_id = a.id";
}
