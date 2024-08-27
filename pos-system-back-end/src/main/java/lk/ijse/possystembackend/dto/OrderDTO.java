package lk.ijse.possystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private String orderDate;
    private double total;
    private double discountValue;
    private double newTotal;
    private String customerId;
}
