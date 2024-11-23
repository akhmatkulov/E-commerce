package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/file/*")
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Integer productId = Integer.parseInt(pathInfo.split("/")[1]);
        Product product = DB.products.stream().filter(item -> item.getId().equals(productId)).findFirst().get();
        resp.getOutputStream().write(Files.readAllBytes(Path.of(product.getPath())));
    }
}
