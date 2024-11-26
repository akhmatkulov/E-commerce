package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.entity.Basket;
import uz.pdp.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/remove")
public class RemoveToBasketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productID = Integer.parseInt(req.getParameter("productID"));
        Product product = DB.products.stream().filter(p -> p.getId() == productID).findFirst().orElseThrow();
        HttpSession session = req.getSession();
        Basket basket = (Basket) Objects.requireNonNullElse(session.getAttribute("basket"), new Basket());
        Map<Product, Integer> mapBasket = basket.getMapBasket();
        mapBasket.remove(product);
        session.setAttribute("basket",basket);
        resp.sendRedirect("main.jsp");
    }
}
