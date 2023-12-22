package org.bezrukov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bezrukov.service.BookNAuthorService;

import java.io.IOException;

public class RestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookNAuthorService service = new BookNAuthorService();
        String book = req.getParameter("book");
        String author = req.getParameter("author");
        service.addBookNAuthor(book, author);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
