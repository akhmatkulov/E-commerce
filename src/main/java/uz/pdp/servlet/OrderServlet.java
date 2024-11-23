package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.OrderStatus;
import uz.pdp.entity.Order;
import uz.pdp.entity.OrderProduct;
import uz.pdp.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        for (Map.Entry<Product, Integer> entry : DB.map.entrySet()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(entry.getKey().getId());
            orderProduct.setQuantity(entry.getValue());
            list.add(orderProduct);
        }
        orderListMap.put(order, list);
        orders.put(id, orderListMap);
               DB.map.clear();
       resp.sendRedirect("/myOrders.jsp");
    }
}
