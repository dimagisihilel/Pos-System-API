package lk.ijse.possystembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
