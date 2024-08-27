package lk.ijse.possystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail {
    private String orderId;
    private String itemId;
    private int qty;
    private double unitPrice;
    private double total;
}
