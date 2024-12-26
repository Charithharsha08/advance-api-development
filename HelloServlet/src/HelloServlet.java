import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet( urlPatterns ="/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get Method triggered");
        PrintWriter out = resp.getWriter();
        out.println("Get Method executed");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST Method triggered");
        PrintWriter out = resp.getWriter();
        out.println("POST Method executed");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("OPTIONS Method triggered");
        PrintWriter out = resp.getWriter();
        out.println("OPTIONS Method executed");     }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DELETE Method triggered");
        PrintWriter out = resp.getWriter();
        out.println("DELETE Method executed");    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PUT Method triggered");
        PrintWriter out = resp.getWriter();
        out.println("PUT Method executed");    }
}
