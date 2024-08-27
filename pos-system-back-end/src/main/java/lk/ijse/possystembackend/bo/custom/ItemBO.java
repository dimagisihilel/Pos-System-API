package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    boolean existItem(String id) throws SQLException, ClassNotFoundException;
    String nextItemId() throws SQLException, ClassNotFoundException;
    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;
}
