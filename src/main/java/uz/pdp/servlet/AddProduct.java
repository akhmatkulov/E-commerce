package uz.pdp.servlet;

import uz.pdp.DB.DB;
import uz.pdp.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@MultipartConfig
@WebServlet("/addProduct")
public class AddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String absolutePath = getServletContext().getRealPath("/") + "uploaded/";
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String categoryId = req.getParameter("categoryId");
        Part part = req.getPart("productPhoto");

        if (name != null && price != null && categoryId != null && part != null) {
            Files.createDirectories(Path.of(absolutePath));

            String fileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
            Path path = Path.of(absolutePath, fileName);

            try (InputStream input = part.getInputStream()) {
                Files.write(path, input.readAllBytes());
            }

            String photoUrl = path.toString();

            Product product = new Product(name, Double.parseDouble(price), Integer.parseInt(categoryId), fileName);
            DB.products.add(product);

            resp.sendRedirect("/addProduct.jsp");
        } else {
            resp.getWriter().write("Error not fully data!");
        }
    }
}
