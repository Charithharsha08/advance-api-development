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
           JsonArrayBuilder allItems = Json.createArrayBuilder();
           while (resultSet.next()) {
               String code = resultSet.getString("code");
               String description = resultSet.getString("description");
               double unit = resultSet.getDouble("unitPrice");
               int qty = resultSet.getInt("qtyOnHand");
               JsonObjectBuilder item = Json.createObjectBuilder();
               item.add("code",code);
               item.add("description",description);
               item.add("unit",unit);
               item.add("qty",qty);
               allItems.add(item.build());
           }
           resp.getWriter().write(allItems.build().toString());
           connection.close();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = CreateConnection.getConnection();
        String code = req.getParameter("code");
        try {
            connection.prepareStatement("delete from item where code = '"+code+"' ").execute();
            resp.sendRedirect("index.html");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = CreateConnection.getConnection();
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        double unit = Double.parseDouble(req.getParameter("unitPrice"));
        int qty = Integer.parseInt(req.getParameter("qtyOnHand"));

        try {
            connection.prepareStatement("insert into item values('"+code+"','"+description+"',"+unit+","+qty+")").execute();
            resp.sendRedirect("index.html");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DoPut Method triggered");
        Connection connection = CreateConnection.getConnection();
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        double unit = Double.parseDouble(req.getParameter("unitPrice"));
        int qty = Integer.parseInt(req.getParameter("qtyOnHand"));
        System.out.println("Code: "+code+" Description: "+description+" Unit: "+unit+" Quantity: "+qty+"");
        try {
            connection.prepareStatement("update item set description='"+description+"',unitPrice="+unit+",qtyOnHand="+qty+" where code='"+code+"' ").execute();
            resp.sendRedirect("item.js");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
