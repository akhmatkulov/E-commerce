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
            new User("akmal", "+998933333333", "3333"),
            new User("bahrom", "+998944444444", "4444"),
            new User("jasur", "+998911111111", "1111")
    ));

    List<Category> CATEGORIES = new ArrayList<>(List.of(
            new Category("Foods"),
            new Category("Drinks"),
            new Category("Clothes"),
            new Category("Electronics")
    ));

    List<Product> products = new ArrayList<>(List.of(
            new Product("Apple", 2, 1, "olma.jpg"),
            new Product("Graphs", 1.5, 1, "uzum.jpg"),
            new Product("Apricot", 2.5, 1, "shaftoli.jpg"),
            new Product("Hydrolife", 0.5, 2, "hydrolife.jpg"),
            new Product("Milliy cola", 1, 2, "milliy cola.jpg"),
            new Product("Flavis", 1, 2, "flavis.jpg")
    ));

    static Product getProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    Map<Product, Integer> map = new HashMap<>();

    Map<Product, Integer> mapOrder = new HashMap<>();

}
