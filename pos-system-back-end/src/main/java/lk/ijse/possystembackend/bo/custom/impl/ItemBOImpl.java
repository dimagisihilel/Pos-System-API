package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.ItemBO;
import lk.ijse.possystembackend.dao.DAOFactory;
import lk.ijse.possystembackend.dao.custom.ItemDAO;
import lk.ijse.possystembackend.dto.ItemDTO;
import lk.ijse.possystembackend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(new ItemDTO(item.getItemId(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        }
        return itemDTOs;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getItemId(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getItemId(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public String nextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.nextId();
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return item != null ? new ItemDTO(item.getItemId(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()) : null;
    }
}
