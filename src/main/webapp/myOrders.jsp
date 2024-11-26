<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="uz.pdp.entity.OrderProduct" %>
<%@ page import="uz.pdp.entity.Product" %>
<%@ page import="uz.pdp.servlet.OrderServlet" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="uz.pdp.DB.DB" %>
<%@ page import="uz.pdp.entity.Order" %>
<html>
<head>
    <title>Orders List</title>
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

        Map<Order, List<OrderProduct>> orderListMap = OrderServlet.orders.get(userId);
        if (orderListMap != null && !orderListMap.isEmpty()) {
            int orderNumber = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    %>
    <% for (Map.Entry<Order, List<OrderProduct>> order: orderListMap.entrySet()) { %>
    <div class="card mb-4">
        <div class="card-header d-flex row">
            <h5 class="col">Order Id #<%= order.getKey().getId() %></h5>
            <p class="mb-0 col">Date: <%= dateFormat.format(order.getKey().getDateTime()) %></p>
            <p class="mb-0 col">Order Status: <%= order.getKey().getStatus() %></p>
            <a href="/orderItem?orderId=<%=order.getKey().getId()%>" type="button" class="btn btn-success col">Show</a>
        </div>

    </div>
    <% } %>
    <% } else { %>
    <p class="text-center text-muted">You have no orders yet.</p>
    <% } %>
</div>
</body>
</html>
