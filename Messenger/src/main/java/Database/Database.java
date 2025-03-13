package Database;

import Models.Message;
import Models.User;

import java.sql.*;
import java.util.ArrayList;

public class Database
{
    private static final Database singleton = new Database();

    private Connection connection;

    private final ArrayList<User> users = new ArrayList <>();
    private final ArrayList<Message> messages = new ArrayList <>();

    private Database()
    {
        final String url = "jdbc:postgresql://localhost:5432/Messenger";
        final String serverUser = "postgres";
        final String serverPassword = "donpollo";

        try
        {
            connection = DriverManager.getConnection(url, serverUser, serverPassword);

            Statement createTable = connection.createStatement();

            createTable.executeUpdate(
                "CREATE TABLE IF NOT EXISTS \"Users\"(" +
                    "\"username\" VARCHAR(40) PRIMARY KEY," +
                    "\"password\" VARCHAR(40))");

            createTable.executeUpdate(
                "CREATE TABLE IF NOT EXISTS \"Messages\"(" +
                    "\"receiver_username\" VARCHAR(40)," +
                    "\"message\" TEXT)");

            Statement queryTable = connection.createStatement();

            var usersQuery = queryTable.executeQuery("SELECT * FROM \"Users\"");

            String username;
            String userPassword;

            while (usersQuery.next())
            {
                username = usersQuery.getString("username");
                userPassword = usersQuery.getString("password");

                users.add(new User(username, userPassword));
            }

            var messagesQuery = queryTable.executeQuery("SELECT * FROM \"Messages\"");
            String receiverUsername;
            String message;

            while (messagesQuery.next())
            {
                receiverUsername = messagesQuery.getString("receiver_username");
                message = messagesQuery.getString("message");

                messages.add(new Message(receiverUsername, message));
            }
        }
        catch (SQLException e)
        {
            System.err.println("SQLException at Database constructor");
            e.printStackTrace();
        }
    }

    public static Database getInstance()
    {
        return singleton;
    }

    public boolean passwordIsCorrect(User user)
    {
        for (User x : users)
        {
            if (x.equals(user))
                return true;
        }

        return false;
    }

    private boolean userExists(String username)
    {
        for (User x : users)
        {
            if (x.username().equals(username)) return true;
        }
        return false;
    }

    //  Returns true when successful

    public boolean addUser(User user)
    {
        try
        {
            for (User x : users)
            {
                if (x.username().equals(user.username())) return false;
            }

            PreparedStatement addUser = connection.prepareCall("INSERT INTO \"Users\" VALUES(?, ?)");

            addUser.setString(1, user.username());
            addUser.setString(2, user.password());

            addUser.executeUpdate();

            users.add(user);

            System.out.println("New user signed in");
            System.out.println("Username: " + user.username());
            System.out.println("Password: " + user.password());
        }
        catch (SQLException e)
        {
            System.err.println("SQLException at Database.addUser()");
        }

        return true;
    }

    public boolean addMessage(Message message)
    {
        try
        {
            if (!userExists(message.receiverUsername())) return false;

            PreparedStatement addMessage = connection.prepareCall("INSERT INTO \"Messages\" VALUES(?, ?)");

            addMessage.setString(1, message.receiverUsername());
            addMessage.setString(2, message.message());

            addMessage.executeUpdate();

            messages.add(message);

            System.out.println("To " + message.receiverUsername() + ": " + message.message());
        }
        catch (SQLException e)
        {
            System.err.println("SQLException at Database.addMessage()");
        }

        return true;
    }

    public ArrayList<String> getMessagesForUser(String receiverUsername)
    {
        ArrayList<String> output = new ArrayList <>();

        for (Message x : messages)
        {
            if (x.receiverUsername().equals(receiverUsername))
            {
                output.add(x.message());
            }
        }

        return output;
    }
}