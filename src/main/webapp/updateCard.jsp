<%@ page import="uz.pdp.entity.Product" %>
<%@ page import="uz.pdp.DB.DB" %>
<%@ page language="java" %>
<%
  String action = request.getParameter("action");
  if (action != null) {
    if (action.startsWith("increase-")) {
      int productId = Integer.parseInt(action.split("-")[1]);
      Product product = DB.products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
      if (product != null) {
        int quantity = DB.map.getOrDefault(product, 0);
        DB.map.put(product, quantity + 1);
      }
    } else if (action.startsWith("decrease-")) {
      int productId = Integer.parseInt(action.split("-")[1]);
      Product product = DB.products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
      if (product != null) {
        int quantity = DB.map.getOrDefault(product, 0);
        if (quantity > 0) {
          DB.map.put(product, quantity - 1);
        }
      }
    } else if ("order".equals(action)) {
    }
  }
  response.sendRedirect("cart.jsp");
%>
