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
import lk.ijse.possystembackend.bo.custom.OrderBO;
import lk.ijse.possystembackend.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String all = req.getParameter("all");
        String nextId = req.getParameter("nextId");  // Check if nextId is requested
        String date = req.getParameter("date");

        try {
            resp.setContentType("application/json");

            if (all != null) {
                resp.getWriter().write(jsonb.toJson(orderBO.getAllOrders()));
            } else if (id != null) {
                resp.getWriter().write(jsonb.toJson(orderBO.searchOrder(id)));
            } else if (nextId != null) {  // If the nextId is requested
                String generatedNextId = orderBO.generateNewOrderId();
                resp.getWriter().write(jsonb.toJson(generatedNextId));
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
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            boolean isSaved = orderBO.placeOrder(orderDTO);

            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Order saved successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to save order");
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
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            boolean isUpdated = orderBO.updateOrder(orderDTO);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Order updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to update order");
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Error processing PUT request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            boolean isDeleted = orderBO.deleteOrder(id);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Order deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to delete order");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error processing DELETE request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
