package uz.pdp.servlet;

import uz.pdp.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class Filter extends HttpFilter {
        List<String> userPages = new ArrayList<>(
                List.of(
                        "/logout",
                        "/orderItem",
                        "/myOrders.jsp",
                        "/orderItem.jsp"
                )
        );

        List<String> adminPages = new ArrayList<>(
                List.of(
                        "/addProduct",
                        "/file/*",
                        "/addCategory.jsp",
                        "/addProduct.jsp",
                        "/admin.jsp"
                )
        );
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        User currentUser = null;
        if (user != null) {
            currentUser = (User) user;
        }
        if (userPages.contains(requestURI)) {
            if (currentUser != null && currentUser.getRole().equals("user")) {
                chain.doFilter(req, res);
            } else {
                res.sendRedirect("/login.jsp");
            }
        } else if (adminPages.contains(requestURI)) {
            if (currentUser != null && currentUser.getRole().equals("admin")) {
                chain.doFilter(req, res);
            } else {
                res.sendRedirect("/login.jsp");
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
