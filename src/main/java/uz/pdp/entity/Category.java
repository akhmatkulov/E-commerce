package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id = genId ++;
    private String name;
    private static Integer genId = 1;

    public Category(String name) {
        this.name = name;
    }
}
