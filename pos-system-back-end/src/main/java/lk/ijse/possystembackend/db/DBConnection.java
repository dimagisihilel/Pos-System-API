package lk.ijse.possystembackend.db;


import lk.ijse.possystembackend.controller.CustomerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    private static DBConnection dbConnection;

    private DBConnection() throws ClassNotFoundException, SQLException {

        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:/comp/env/jdbc/possystem");
            pool.getConnection();

            this.connection = pool.getConnection();
        } catch (Exception e) {
            logger.error("faild with: ",e.getMessage());
            e.printStackTrace();
        }
    }

    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? dbConnection= new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}