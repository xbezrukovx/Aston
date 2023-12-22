<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h2>Books N Authors!</h2>
    <%
       org.bezrukov.service.BookNAuthorService service = new org.bezrukov.service.BookNAuthorService();
       java.util.List list = service.getAll();
       for(Object o : list) {
        org.bezrukov.dto.BookNAuthor obj = (org.bezrukov.dto.BookNAuthor) o;
        out.println("<p>Author: "+obj.getAuthor() + " / Book: " + obj.getBook() + "</p>");
       }
    %>

</body>
</html>
