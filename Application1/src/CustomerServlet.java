import DTO.CustomerDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    List<CustomerDTO> customerDTOS =new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = CreateConnection.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from customer").executeQuery();
            resp.setContentType("application/json");
            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id",id);
                customer.add("name",name);
                customer.add("address",address);
                allCustomers.add(customer.build());
            }
            resp.getWriter().write(allCustomers.build().toString());
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = CreateConnection.getConnection();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        System.out.println(id + name + address + "put");
        try {
            connection.prepareStatement("update customer set name='" + name + "',address='" + address + "' where id='" + id + "'").executeUpdate();
            resp.sendRedirect("index.html");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = CreateConnection.getConnection();
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            System.out.println(name + address);
            connection.prepareStatement("insert into customer values('" + id + "','" + name + "','" + address + "')").executeUpdate();
            resp.sendRedirect("index.html");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Connection connection = CreateConnection.getConnection();
      String id = req.getParameter("id");
      try {
          connection.prepareStatement("delete from customer where id = '" + id + "'").executeUpdate();
resp.sendRedirect("index.html");
connection.close();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

}