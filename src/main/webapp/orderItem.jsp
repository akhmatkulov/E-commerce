<%@ page import="uz.pdp.servlet.OrderServlet" %>
<%@ page import="uz.pdp.entity.OrderProduct" %>
<%@ page import="uz.pdp.servlet.OrderItemServlet" %>
<%@ page import="uz.pdp.DB.DB" %>
<%@ page import="uz.pdp.entity.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OrderItem</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <a href="/main.jsp" class="btn btn-secondary position-absolute" style="top: 20px; left: 20px; padding: 10px 20px; font-size: 1rem; border-radius: 8px;">
        &larr; Back
    </a>
    <h1 class="text-center mb-4">Order History</h1>

    <%
        int userId = 0;
        for (javax.servlet.http.Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("userId")) {
                userId = Integer.parseInt(cookie.getValue());
            }
        }
        if (userId == 0) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <% for (OrderProduct orderProduct: OrderItemServlet.orderProducts) { %>
    <div class="card mb-4">
        <div class="card-header d-flex row">
            <h5 class="col">ProductId #<%= orderProduct.getProductId() %></h5>
            <% Product productById = DB.getProductById(orderProduct.getProductId()); %>
            <p class="mb-0 col">Name: <%= productById.getName() %></p>
            <p class="mb-0 col">Price: <%= productById.getPrice() %>$</p>
            <p class="col">Quantity: <%= orderProduct.getQuantity()%></p>
            <p class="col">Category Id: <%= productById.getCategoryId() %></p>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>