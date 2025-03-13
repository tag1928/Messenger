package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class IndexServlet extends HttpServlet
{
    private static IndexServlet singleton = new IndexServlet();

    private IndexServlet(){}

    public static IndexServlet getInstance()
    {
        return singleton;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.sendRedirect("http://localhost:8080/index.html");
    }
}