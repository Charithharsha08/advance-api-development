import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {
    public static Connection getConnection()  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}