package battleships;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JB
 */
public class Connect {

    public static Connection connect() throws SQLException {
        Connection connection;
        String url = "jdbc:mysql://192.168.0.13:3306/Battleships";
        String username = "javaMAC";
        String password = "java";

        System.out.println("Connecting to database...");

        connection = DriverManager.getConnection(url, username, password);

        if (connection.isValid(5)) {
            System.out.println("Database connected successfully!");
        }

        return connection;
    }
}
