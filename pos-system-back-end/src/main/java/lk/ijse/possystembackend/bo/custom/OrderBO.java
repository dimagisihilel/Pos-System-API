package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;
    OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException;
    boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
}
