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
import lk.ijse.possystembackend.bo.custom.CustomerBO;
import lk.ijse.possystembackend.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String all = req.getParameter("all");
        String search = req.getParameter("search");
        String nextId = req.getParameter("nextid");

        try {
            if (all != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(customerBO.getAllCustomers()));
            } else if (id != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(customerBO.searchCustomer(id)));
            } else if (search != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(customerBO.searchCustomers(search)));
            } else if (nextId != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(customerBO.nextCustomerId()));
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
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            boolean isSaved = customerBO.saveCustomer(customerDTO);

            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Customer saved successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to save customer");
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
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            boolean isUpdated = customerBO.updateCustomer(customerDTO);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Customer updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to update customer");
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
            boolean isDeleted = customerBO.deleteCustomer(id);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Customer deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to delete customer");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error processing DELETE request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
