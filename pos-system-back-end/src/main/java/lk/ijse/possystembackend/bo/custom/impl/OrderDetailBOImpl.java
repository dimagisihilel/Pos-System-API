package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.OrderDetailBO;
import lk.ijse.possystembackend.dao.DAOFactory;
import lk.ijse.possystembackend.dao.custom.OrderDetailDAO;
import lk.ijse.possystembackend.dto.OrderDetailDTO;
import lk.ijse.possystembackend.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {

    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException ,ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails = orderDetailDAO.getAll();
        ArrayList<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDTOs.add(new OrderDetailDTO(
                    orderDetail.getOrderId(),
                    orderDetail.getItemId(),
                    orderDetail.getQty(),
                    orderDetail.getUnitPrice(),
                    orderDetail.getTotal()
            ));
        }
        return orderDetailDTOs;
    }

    @Override
    public OrderDetailDTO searchOrderDetail(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail = orderDetailDAO.search(orderId, itemId);
        if (orderDetail != null) {
            return new OrderDetailDTO(
                    orderDetail.getOrderId(),
                    orderDetail.getItemId(),
                    orderDetail.getQty(),
                    orderDetail.getUnitPrice(),
                    orderDetail.getTotal()
            );
        }
        return null;
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.save(new OrderDetail(
                orderDetailDTO.getOrderId(),
                orderDetailDTO.getItemId(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getUnitPrice(),
                orderDetailDTO.getTotal()
        ));
    }

    @Override
    public boolean updateOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.update(new OrderDetail(
                orderDetailDTO.getOrderId(),
                orderDetailDTO.getItemId(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getUnitPrice(),
                orderDetailDTO.getTotal()
        ));
    }

    @Override
    public boolean deleteOrderDetail(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.delete(orderId, itemId);
    }
}
