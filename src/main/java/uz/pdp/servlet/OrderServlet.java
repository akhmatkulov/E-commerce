package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.OrderStatus;
import uz.pdp.entity.Basket;
import uz.pdp.entity.Order;
import uz.pdp.entity.OrderProduct;
import uz.pdp.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/order")
public class    OrderServlet extends HttpServlet {

    // Har bir foydalanuvchi buyurtmalarini saqlash
    public static Map<Integer, Map<Order, List<OrderProduct>>> orders = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("userId")) {
                id = Integer.parseInt(cookie.getValue());
            }
        }
        if (id == 0) {
            resp.sendRedirect("login");
            return;
        }

        if (!orders.containsKey(id)) {
            orders.put(id, new HashMap<>());
        }
        Map<Order, List<OrderProduct>> orderListMap = orders.get(id);
        Order order = new Order();
        order.setUserId(id);
        order.setDateTime(new Date());
        order.setStatus(OrderStatus.NEW);
        List<OrderProduct> list = new ArrayList<>();
        HttpSession session = req.getSession();
        Basket basket = (Basket) Objects.requireNonNullElse(session.getAttribute("basket"), new Basket());
        Map<Product, Integer> mapBasket = basket.getMapBasket();
        for (Map.Entry<Product, Integer> entry : mapBasket.entrySet()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(entry.getKey().getId());
            orderProduct.setQuantity(entry.getValue());
            list.add(orderProduct);
        }
        orderListMap.put(order, list);
        orders.put(id, orderListMap);
        session.removeAttribute("basket");
       resp.sendRedirect("/myOrders.jsp");
    }
}
