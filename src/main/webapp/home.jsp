<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <form action="/webApp/hello" method="get" class="form-example">
      <div class="form-example">
        <label for="name">Enter your name: </label>
        <input type="text" name="book" id="book" required />
      </div>
      <div class="form-example">
        <label for="email">Enter your email: </label>
        <input type="text" name="authors" id="authors" required />
      </div>
      <div class="form-example">
        <input type="submit" value="Send!" />
      </div>
    </form>

    <h2>Books N Authors!</h2>
    <%
        org.bezrukov.services.BookService service = new org.bezrukov.services.BookService();
        java.util.List<org.bezrukov.dto.AuthorDTO> list = service.getAuthors();
        out.println("<table>");
        for(org.bezrukov.dto.AuthorDTO o : list) {
            java.util.List<org.bezrukov.dto.BookShortDTO> books = o.getBooks();
            for(org.bezrukov.dto.BookShortDTO book : books){
                out.println("<tr>");
                out.println("<td>" + o.getName() + "</td>");
                out.println("<td>" + book.getName() + "</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
    %>

</body>
</html>
