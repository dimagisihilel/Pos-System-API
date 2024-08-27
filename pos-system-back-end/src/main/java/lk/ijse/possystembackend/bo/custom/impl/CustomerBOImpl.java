package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.bo.custom.CustomerBO;
import lk.ijse.possystembackend.dao.DAOFactory;
import lk.ijse.possystembackend.dao.custom.CustomerDAO;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO{

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll().stream()
                .map(c -> new CustomerDTO(c.getCustomerid(), c.getName(), c.getAddress(), c.getContact()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCustomerid(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDTO.getCustomerid(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getCustomerid(), customer.getName(), customer.getAddress(), customer.getContact());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String search) throws SQLException, ClassNotFoundException {
        return customerDAO.getAll().stream()
                .filter(c -> c.getName().contains(search) || c.getAddress().contains(search) || c.getContact().contains(search))
                .map(c -> new CustomerDTO(c.getCustomerid(), c.getName(), c.getAddress(), c.getContact()))
                .collect(Collectors.toList());
    }

    @Override
    public String nextCustomerId() throws SQLException, ClassNotFoundException {
        // Implement logic to generate the next customer ID
        return customerDAO.nextId();
    }
}
