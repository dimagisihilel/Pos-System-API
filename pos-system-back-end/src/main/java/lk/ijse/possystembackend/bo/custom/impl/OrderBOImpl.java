package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.OrderBO;
import lk.ijse.possystembackend.dao.DAOFactory;
import lk.ijse.possystembackend.dao.custom.OrderDAO;
import lk.ijse.possystembackend.dao.custom.OrderDetailDAO;
import lk.ijse.possystembackend.dto.OrderDTO;
import lk.ijse.possystembackend.entity.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {

        // Generate the next order ID
        String orderId = orderDAO.nextId();
        orderDTO.setOrderId(orderId);

        // Set the current date
        String currentDate = LocalDate.now().toString();
        orderDTO.setOrderDate(currentDate);

        // Convert OrderDTO to Order entity and save
        Order order = new Order(orderDTO.getOrderId(), orderDTO.getOrderDate(), orderDTO.getTotal(),
                orderDTO.getDiscountValue(), orderDTO.getNewTotal(), orderDTO.getCustomerId());
        return orderDAO.save(order);

    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getTotal(),
                    order.getDiscountValue(),
                    order.getNewTotal(),
                    order.getCustomerId()
            ));
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
        Order order = orderDAO.search(id);
        if (order != null) {
            return new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getTotal(),
                    order.getDiscountValue(),
                    order.getNewTotal(),
                    order.getCustomerId()
            );
        }
        return null;
    }

    @Override
    public boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(
                orderDTO.getOrderId(),
                orderDTO.getOrderDate(),
                orderDTO.getTotal(),
                orderDTO.getDiscountValue(),
                orderDTO.getNewTotal(),
                orderDTO.getCustomerId()
        ));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.nextId();
    }

}
