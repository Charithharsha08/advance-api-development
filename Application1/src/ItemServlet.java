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
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = CreateConnection.getConnection();
        try {
           ResultSet resultSet = connection.prepareStatement("select * from item").executeQuery();
            resp.setContentType("application/json");
            JsonArrayBuilder allItem = Json.createArrayBuilder();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                JsonObjectBuilder item = Json.createObjectBuilder();
                item.add("id",id);
                item.add("name",name);
                item.add("price",price);
                allItem.add(item.build());
            }
            resp.getWriter().write(allItem.build().toString());
            resp.sendRedirect("index.html");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
