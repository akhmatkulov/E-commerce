<%@ page import="uz.pdp.entity.Product" %>
<%@ page import="uz.pdp.DB.DB" %>
<%@ page import="java.util.Map" %>
<%@ page import="uz.pdp.entity.Basket" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" %>
<%
  String action = request.getParameter("action");
  session = request.getSession();
  Basket basket = (Basket) Objects.requireNonNullElse(session.getAttribute("basket"), new Basket());
  Map<Product, Integer> mapBasket = basket.getMapBasket();
  if (action != null) {
    if (action.startsWith("increase-")) {
      int productId = Integer.parseInt(action.split("-")[1]);
      Product product = DB.products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
      if (product != null) {
        int quantity = mapBasket.getOrDefault(product, 0);
        mapBasket.put(product, quantity + 1);
      }
    } else if (action.startsWith("decrease-")) {
      int productId = Integer.parseInt(action.split("-")[1]);
      Product product = DB.products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
      if (product != null) {
        int quantity = mapBasket.getOrDefault(product, 0);
        if (quantity > 1) {
          mapBasket.put(product, quantity - 1);
        } else {
          mapBasket.remove(product);
//          session.setAttribute("basket",basket);
        }
      }
    } else if ("order".equals(action)) {
    }
  }
  response.sendRedirect("basket.jsp");
%>
