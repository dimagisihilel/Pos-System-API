package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.SQLUtil;
import lk.ijse.possystembackend.dao.custom.OrderDetailDAO;
import lk.ijse.possystembackend.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM order_details");
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        while (rst.next()) {
            orderDetails.add(new OrderDetail(rst.getString(1), rst.getString(2),
                    rst.getInt(3), rst.getDouble(4),
                    rst.getDouble(5)));
        }
        return orderDetails;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO order_details VALUES (?, ?, ?, ?, ?)",
                orderDetail.getOrderId(), orderDetail.getItemId(), orderDetail.getQty(),
                orderDetail.getUnitPrice(), orderDetail.getTotal());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE order_details SET qty=?, unit_price=?, total=? WHERE order_id=? AND item_id=?",
                orderDetail.getQty(), orderDetail.getUnitPrice(), orderDetail.getTotal(),
                orderDetail.getOrderId(), orderDetail.getItemId());
    }


    @Override
    public boolean delete(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM order_details WHERE order_id=? AND item_id=?", orderId, itemId);
    }

    @Override
    public boolean exist(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id, item_id FROM order_details WHERE order_id=? AND item_id=?", orderId, itemId);
        return rst.next();
    }

    @Override
    public OrderDetail search(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM order_details WHERE order_id=? AND item_id=?", orderId, itemId);
        if (rst.next()) {
            return new OrderDetail(rst.getString(1), rst.getString(2), rst.getInt(3),
                    rst.getDouble(4), rst.getDouble(5));
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String nextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
