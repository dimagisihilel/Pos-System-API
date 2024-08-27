package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.SQLUtil;
import lk.ijse.possystembackend.dao.custom.CustomerDAO;
import lk.ijse.possystembackend.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = SQLUtil.execute(sql);
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getString("customer_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("contact")
            ));
        }
        return customers;
    }

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer (customer_id, name, address, contact) VALUES (?, ?, ?, ?)";
        return SQLUtil.execute(sql, dto.getCustomerid(), dto.getName(), dto.getAddress(), dto.getContact());

    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name = ?, address = ?, contact = ? WHERE customer_id = ?";
        return SQLUtil.execute(sql, dto.getName(), dto.getAddress(), dto.getContact(), dto.getCustomerid());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return SQLUtil.execute(sql, id);
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM customer WHERE customer_id = ?";
        ResultSet resultSet = SQLUtil.execute(sql, id);
        return resultSet.next();
    }

    @Override
    public String nextId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM customer ORDER BY customer_id DESC LIMIT 1";
        ResultSet resultSet = SQLUtil.execute(sql);
        if (resultSet.next()) {
            return String.format("C%03d", Integer.parseInt(resultSet.getString(1).substring(1)) + 1);
        } else {
            return "C001";
        }
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        ResultSet resultSet = SQLUtil.execute(sql, id);
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString("customer_id"),  //methana kalin thibbe id
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("contact")
            );
        }
        return null;
    }
}
