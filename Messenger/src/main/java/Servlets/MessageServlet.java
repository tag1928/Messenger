package Servlets;

import Database.Database;
import Models.Message;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MessageServlet extends HttpServlet
{
    private static final MessageServlet singleton = new MessageServlet();

    private MessageServlet(){}

    public static MessageServlet getInstance()
    {
        return singleton;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!Database.getInstance().passwordIsCorrect(new User(username, password)))
        {
            resp.setStatus(403);
            return;
        }

        resp.setContentType("application/json");

        mapper.writeValue(resp.getWriter(),
            Database.getInstance().getMessagesForUser(username));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        Message message = mapper.readValue(req.getReader(), Message.class);

        if (!Database.getInstance().addMessage(message))
            resp.setStatus(403);
    }
}