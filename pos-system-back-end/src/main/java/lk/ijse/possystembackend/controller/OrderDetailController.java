package lk.ijse.possystembackend.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystembackend.bo.BOFactory;
import lk.ijse.possystembackend.bo.custom.OrderDetailBO;
import lk.ijse.possystembackend.dto.OrderDetailDTO;
import lk.ijse.possystembackend.entity.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order-detail")
public class OrderDetailController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    private final OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        String itemId = req.getParameter("itemId");
        String all = req.getParameter("all");

        try {
            if (all != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(orderDetailBO.getAllOrderDetails()));
            } else if (orderId != null && itemId != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(orderDetailBO.searchOrderDetail(orderId, itemId)));
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Error processing GET request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try {
            OrderDetailDTO orderDetailDTO = jsonb.fromJson(req.getReader(), OrderDetailDTO.class);
            boolean isSaved = orderDetailBO.saveOrderDetail(orderDetailDTO);

            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Order detail saved successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to save order detail");
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Error processing POST request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try {
            OrderDetailDTO orderDetailDTO = jsonb.fromJson(req.getReader(), OrderDetailDTO.class);
            boolean isUpdated = orderDetailBO.updateOrderDetail(orderDetailDTO);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Order detail updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to update order detail");
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Error processing PUT request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        String itemId = req.getParameter("itemId");

        try {
            boolean isDeleted = orderDetailBO.deleteOrderDetail(orderId, itemId);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Order detail deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to delete order detail");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error processing DELETE request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
