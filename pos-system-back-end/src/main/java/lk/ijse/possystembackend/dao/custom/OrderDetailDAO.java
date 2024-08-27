package lk.ijse.possystembackend.dao.custom;

import lk.ijse.possystembackend.dao.CrudDAO;
import lk.ijse.possystembackend.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
     boolean delete(String orderId, String itemId) throws SQLException, ClassNotFoundException;

     boolean exist(String orderId, String itemId) throws SQLException, ClassNotFoundException;
     OrderDetail search(String orderId, String itemId) throws SQLException, ClassNotFoundException;
}
