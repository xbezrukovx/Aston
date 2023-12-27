package org.bezrukov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bezrukov.dto.AuthorShortDTO;
import org.bezrukov.dto.BookShortDTO;
import org.bezrukov.services.BookService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RestServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookName = req.getParameter("book");
        String authorNames = req.getParameter("authors");

        BookShortDTO bookShortDTO = new BookShortDTO(bookName);
        List<AuthorShortDTO> authors = Arrays.stream(authorNames.split(","))
                .map(AuthorShortDTO::new)
                .toList();
        authors.forEach(a -> {
            System.out.println(a.getName());
        });

        bookService.addBook(bookShortDTO, authors);

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
