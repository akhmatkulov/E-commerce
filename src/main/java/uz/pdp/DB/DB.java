package uz.pdp.DB;

import uz.pdp.entity.Category;
import uz.pdp.entity.Product;
import uz.pdp.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DB {
    List<User> USERS = new ArrayList<>(List.of(
            new User("akmal", "+998933333333", "3333","user"),
            new User("bahrom", "+998944444444", "4444","user"),
            new User("admin", "+998911111111", "1111","admin")
    ));

    List<Category> CATEGORIES = new ArrayList<>(List.of(
            new Category("Fruits"),
            new Category("Foods"),
            new Category("Drinks"),
            new Category("Clothes")
    ));

    List<Product> products = new ArrayList<>(List.of(
            new Product("Apple", 2, 1, "olma.jpg"),
            new Product("Grape", 1.5, 1, "uzum.jpg"),
            new Product("Peach", 2.5, 1, "shaftoli.jpg"),
            new Product("Free", 2.5, 2, "free.jpg"),
            new Product("Hamburger", 3.5, 2, "hamburger.jpg"),
            new Product("Hydrolife", 0.5, 3, "hydrolife.jpg"),
            new Product("Milliy cola", 1, 3, "milliy cola.jpg"),
            new Product("Flavis", 1, 3, "flavis.jpg")
    ));

    static Product getProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    Map<Product, Integer> map = new HashMap<>();

    Map<Product, Integer> mapOrder = new HashMap<>();

}
