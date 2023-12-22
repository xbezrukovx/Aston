package org.bezrukov.service;

import org.bezrukov.dao.author.AuthorDAO;
import org.bezrukov.dao.book.BookDAO;
import org.bezrukov.dao.booknauthor.BooksNAuthorsDAO;
import org.bezrukov.dto.BookNAuthor;
import org.bezrukov.model.Author;
import org.bezrukov.model.Book;
import org.bezrukov.model.BooksNAuthors;

import java.util.List;

public class BookNAuthorService {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final BooksNAuthorsDAO booksNAuthorsDAO;

    public BookNAuthorService() {
        this.authorDAO = new AuthorDAO();
        this.bookDAO = new BookDAO();
        this.booksNAuthorsDAO = new BooksNAuthorsDAO();
    }

    public Book addBook(String name) {
        Book book = new Book();
        book.setName(name);
        return bookDAO.save(book);
    }

    public Author addAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return authorDAO.save(author);
    }

    public void addBookNAuthor(String bookName, String authorName) {
        Book book = addBook(bookName);
        Author author = addAuthor(authorName);
        BooksNAuthors booksNAuthors = new BooksNAuthors();
        booksNAuthors.setBookId(book.getId());
        booksNAuthors.setAuthorId(author.getId());
        booksNAuthorsDAO.save(booksNAuthors);
    }

    public List<BookNAuthor> getAll() {
        return booksNAuthorsDAO.getAll();
    }
}
