package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            resp.sendRedirect("/main.jsp");
        } else if (!phoneNumber.isEmpty() && !password.isEmpty() &&
                   phoneNumber.equals("+998977777777") && password.equals("7777")) {
            String key = "youneverfindit";
            Base64.Encoder encoder = Base64.getEncoder();
            String string = encoder.encodeToString(key.getBytes());
            resp.addCookie(new Cookie("admin",string));
            resp.sendRedirect("/admin.jsp");
        } else {
            resp.sendRedirect("/login.jsp");
        }
    }

}
