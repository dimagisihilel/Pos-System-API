package lk.ijse.possystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements Serializable {
    private String customerid;
    private String name;
    private String address;
    private String contact;

}
