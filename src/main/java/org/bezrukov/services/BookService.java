package org.bezrukov.services;

import org.bezrukov.dao.AuthorDAO;
import org.bezrukov.dao.BookDAO;
import org.bezrukov.dto.AuthorDTO;
import org.bezrukov.dto.AuthorShortDTO;
import org.bezrukov.dto.BookShortDTO;
import org.bezrukov.entity.Author;
import org.bezrukov.entity.Book;
import org.bezrukov.entity.PaperBook;

import java.util.List;

public class BookService {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;

    public BookService() {
        this.authorDAO = new AuthorDAO();
        this.bookDAO = new BookDAO();
    }

    public void addBook(BookShortDTO book, List<AuthorShortDTO> authors) {
        Book paperBook = new PaperBook();
        List<Author> authorList = authors
                .stream()
                .map(a -> authorDAO.getAuthorByName(a.getName()))
                .toList();
        paperBook.setName(book.getName());

        bookDAO.add(paperBook, authorList);
    }

    public List<AuthorDTO> getAuthors() {
        return authorDAO.getAll().stream()
                .map(a -> {
                    List<BookShortDTO> books = a.getBooks().stream()
                            .map(b -> new BookShortDTO(b.getName()))
                            .toList();
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setName(a.getName());
                    authorDTO.setBooks(books);
                    return authorDTO;
                }).toList();
    }
}
