import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/dbcp")
public class DBCPServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/company");
        ds.setUsername("root");
        ds.setPassword("Ijse@123");
        ds.setMaxTotal(10);
        ds.setInitialSize(10);

        ServletContext context = req.getServletContext();
        context.setAttribute("dataSource", ds);

        try {
            Connection connection = ds.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from Customer").executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                System.out.println("id : " + id + ", name : " + name + ", address : " + address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("dataSource");

        try {
            Connection connection = ds.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from Customer").executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                System.out.println("id : " + id + ", name : " + name + ", address : " + address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
