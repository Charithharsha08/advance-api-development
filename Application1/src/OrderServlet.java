import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = CreateConnection.getConnection();
        String orderId = req.getParameter("orderId");
        Date date = Date.valueOf(req.getParameter("orderDate"));
        String cusId = req.getParameter("customerId");
        System.out.println("Order Id: "+orderId+" Date: "+date+" Customer Id: "+cusId+"");
        try {
            connection.prepareStatement("insert into Orders values('"+orderId+"','"+date+"','"+cusId+"')").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("index.html");
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
