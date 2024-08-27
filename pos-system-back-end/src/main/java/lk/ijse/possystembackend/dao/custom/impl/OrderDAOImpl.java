package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.SQLUtil;
import lk.ijse.possystembackend.dao.custom.OrderDAO;
import lk.ijse.possystembackend.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(rst.getString(1), rst.getString(2), rst.getDouble(3),
                    rst.getDouble(4), rst.getDouble(5), rst.getString(6)));
        }
        return orders;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?)",
                order.getOrderId(), order.getOrderDate(), order.getTotal(),
                order.getDiscountValue(), order.getNewTotal(), order.getCustomerId());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orders SET order_date=?, total=?, discount_value=?, new_total=?, customer_id=? WHERE order_id=?",
                order.getOrderDate(), order.getTotal(), order.getDiscountValue(),
                order.getNewTotal(), order.getCustomerId(), order.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orders WHERE order_id=?", id);
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders WHERE order_id=?", id);
        return rst.next();
    }

    @Override
    public String nextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            try {
                // Extract numeric part from ID
                int numericPart = Integer.parseInt(lastId.substring(2));
                return String.format("O%03d", numericPart + 1);
            } catch (NumberFormatException e) {
                throw new SQLException("Failed to parse order ID: " + lastId, e);
            }
        } else {
            return "O001";
        }
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE order_id=?", id);
        if (rst.next()) {
            return new Order(rst.getString(1), rst.getString(2), rst.getDouble(3),
                    rst.getDouble(4), rst.getDouble(5), rst.getString(6));
        }
        return null;
    }

}
