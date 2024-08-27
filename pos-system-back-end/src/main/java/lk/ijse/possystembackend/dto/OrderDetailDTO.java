package lk.ijse.possystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO {
    private String orderId;
    private String itemId;
    private int qty;
    private double unitPrice;
    private double total;
}
