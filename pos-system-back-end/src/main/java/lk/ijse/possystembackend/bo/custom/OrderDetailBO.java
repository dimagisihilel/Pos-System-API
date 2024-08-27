package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException;
    OrderDetailDTO searchOrderDetail(String orderId, String itemId) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException;
    boolean updateOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException;
    boolean deleteOrderDetail(String orderId, String itemId) throws SQLException, ClassNotFoundException;

}
