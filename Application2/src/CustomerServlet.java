import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customerJson")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("JSON Get Method triggered");
        try {

       /* JsonObjectBuilder customer1 = Json.createObjectBuilder();
        customer1.add("id", 1);
        customer1.add("name", "John Doe");
        customer1.add("address", "123 Main St");

        JsonObjectBuilder customer2 = Json.createObjectBuilder();
            customer2.add("id", 2);
            customer2.add("name", "John Doe2");
            customer2.add("address", "123 Main St2");

        JsonArrayBuilder cust = Json.createArrayBuilder();
        cust.add(customer1.build());
        cust.add(customer2.build());

        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("customers", cust);
        response.add("status", HttpServletResponse.SC_OK);
        response.add("message", "Success");

        JsonObject json = response.build();
        resp.setContentType("application/json");
        resp.getWriter().print(json);*/

            Connection connection = dataSource.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from customer").executeQuery();
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
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data", allCustomers);
            response.add("status", HttpServletResponse.SC_OK);
            response.add("message", "Success");

            JsonObject json = response.build();
            resp.setContentType("application/json");
            resp.getWriter().print(json);

        } catch (Exception e) {
JsonObjectBuilder response = Json.createObjectBuilder();
response.add("data", "");
response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.add("message", "Internal Server Error");
        JsonObject json1 = response.build();
        resp.setContentType("application/json");
        resp.getWriter().print(json1);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");

        System.out.println("id : " + id + ", name : " + name + ", address : " + address);

            Connection connection = dataSource.getConnection();
            connection.prepareStatement("insert into customer values('" + id + "','" + name + "','" + address + "')")
                    .executeUpdate();
            JsonObjectBuilder response = Json.createObjectBuilder();
       response.add("data", jsonObject);
        response.add("status", HttpServletResponse.SC_CREATED);
        response.add("message", "Success");
        resp.getWriter().print(response.build());


        } catch (Exception e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data", "");
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "Internal Server Error");
            JsonObject json1 = response.build();
            resp.setContentType("application/json");
            resp.getWriter().print(json1);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            JsonReader jsonReader = Json.createReader(req.getReader());
            JsonObject jsonObject = jsonReader.readObject();
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String address = jsonObject.getString("address");

            System.out.println("id : " + id + ", name : " + name + ", address : " + address);

            Connection connection = dataSource.getConnection();
            connection.prepareStatement("update customer set name='" + name + "',address='" + address + "' where id='" + id + "'")
                    .executeUpdate();

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data", jsonObject);
            response.add("status", HttpServletResponse.SC_CREATED);
            response.add("message", "Success");
            resp.getWriter().print(response.build());


        } catch (Exception e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data", "");
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "Internal Server Error");
            JsonObject json1 = response.build();
            resp.setContentType("application/json");
            resp.getWriter().print(json1);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JsonReader jsonReader = Json.createReader(req.getReader());
            JsonObject jsonObject = jsonReader.readObject();
            String id = jsonObject.getString("id");

            Connection connection = dataSource.getConnection();
            connection.prepareStatement("delete from customer where id = '" + id + "'").executeUpdate();

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data", jsonObject);
            response.add("status", HttpServletResponse.SC_ACCEPTED);
            response.add("message", "Deleted");
            resp.getWriter().print(response.build());
            connection.close();

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("data","");
            response.add("status",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message",e.getMessage());
            resp.getWriter().print(response.build());
        }
    }
}