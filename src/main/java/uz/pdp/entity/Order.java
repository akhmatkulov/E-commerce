package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.OrderStatus;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id = idGen ++;
    private OrderStatus status;
    Date dateTime;
    private Integer userId;
    private static Integer idGen = 1;


}
