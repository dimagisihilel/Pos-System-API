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
import lk.ijse.possystembackend.bo.custom.ItemBO;
import lk.ijse.possystembackend.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String all = req.getParameter("all");
        String nextId = req.getParameter("nextid");

        try {
            if (all != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(itemBO.getAllItems()));
            } else if (id != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(itemBO.searchItem(id)));
            } else if (nextId != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson(itemBO.nextItemId()));
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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            boolean isSaved = itemBO.saveItem(itemDTO);

            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Item saved successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to save item");
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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            boolean isUpdated = itemBO.updateItem(itemDTO);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Item updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to update item");
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
            boolean isDeleted = itemBO.deleteItem(id);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Item deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Failed to delete item");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error processing DELETE request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
