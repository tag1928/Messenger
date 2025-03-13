package Servlets;

import Database.Database;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServlet extends HttpServlet
{
    private static final UserServlet singleton = new UserServlet();

    private UserServlet(){}

    public static UserServlet getInstance()
    {
        return singleton;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        User user = mapper.readValue(req.getReader(), User.class);

        if (!Database.getInstance().addUser(user)) resp.setStatus(403);
    }
}