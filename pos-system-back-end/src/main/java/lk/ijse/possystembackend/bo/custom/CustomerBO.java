package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;
    List<CustomerDTO> searchCustomers(String search) throws SQLException, ClassNotFoundException;
    String nextCustomerId() throws SQLException, ClassNotFoundException;
}
