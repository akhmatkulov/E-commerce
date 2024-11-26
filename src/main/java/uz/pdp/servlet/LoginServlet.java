package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static User currentUser;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String phoneNumber = req.getParameter("phone");
        String password = req.getParameter("password");

        Optional<User> userOptional = DB.USERS.stream()
                .filter(item -> item.getPhoneNumber().equals(phoneNumber)
                && item.getPassword().equals(password)).findFirst();

        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            String key = "youneverfinduser";
            Base64.Encoder encoder = Base64.getEncoder();
            String string = encoder.encodeToString(key.getBytes());
            Cookie cookie = new Cookie("userId", String.valueOf(currentUser.getId()));
            resp.addCookie(cookie);
            resp.addCookie(new Cookie("user",string));
            HttpSession session = req.getSession();
            Object basket = session.getAttribute("basket");
            setUserToSession(req, userOptional.get());
            if (currentUser.getRole().equals("admin")) {
                System.out.printf("admin");
                resp.sendRedirect("/admin.jsp");
                return;
            } else {
                if (basket == null) {
                    resp.sendRedirect("/main.jsp");
                } else {
                    resp.sendRedirect("/basket.jsp");
                }
            }
        } else {
            resp.sendRedirect("/login.jsp");
        }
    }

    private void setUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

}
