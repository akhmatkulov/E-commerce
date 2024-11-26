package uz.pdp.servlet;

import uz.pdp.entity.Order;
import uz.pdp.entity.OrderProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/orderItem")
public class OrderItemServlet extends HttpServlet {
    public static List<OrderProduct> orderProducts;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        int id = 0;
        if (orderId != null) {
            id = Integer.parseInt(orderId);
        }

        int userId = 0;
        for (javax.servlet.http.Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("userId")) {
                userId = Integer.parseInt(cookie.getValue());
            }
        }
        if (userId == 0 || id == 0) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Map<Order, List<OrderProduct>> orderListMap = OrderServlet.orders.get(userId);
        List<OrderProduct> orderProducts = null;
        for (Map.Entry<Order, List<OrderProduct>> order: orderListMap.entrySet()) {
            if (order.getKey().getId() == id) {
                orderProducts = order.getValue();
            }
        }
        OrderItemServlet.orderProducts = orderProducts;
        resp.sendRedirect("/orderItem.jsp");
    }
}
