package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.custom.ItemDAO;
import lk.ijse.possystembackend.entity.Item;
import lk.ijse.possystembackend.dao.SQLUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> itemList = new ArrayList<>();
        while (rst.next()) {
            itemList.add(new Item(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4)));
        }
        return itemList;
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item (item_id, description, unit_price, qty_on_hand) VALUES (?, ?, ?, ?)",
                item.getItemId(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET description = ?, unit_price = ?, qty_on_hand = ? WHERE item_id = ?",
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getItemId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM item WHERE item_id = ?", id);
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT item_id FROM item WHERE item_id = ?", id);
        return rst.next();
    }

    @Override
    public String nextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        }
        return "I001";
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item WHERE item_id = ?", id);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4));
        }
        return null;
    }
}