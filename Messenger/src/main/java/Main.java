import Servlets.IndexServlet;
import Servlets.MessageServlet;
import Servlets.UserServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Main
{
    public static void main(String[] args)
    {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir("Tomcat");

        final String docBase =
            "C:\\Users\\wwwta\\Desktop\\Java\\MziuriJava2\\Project3\\Messenger\\src\\main\\docBase";

        Context context = tomcat.addContext("", docBase);

        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
        Tomcat.addServlet(context, "MessageServlet", MessageServlet.getInstance());
        Tomcat.addServlet(context, "UserServlet", UserServlet.getInstance());
        Tomcat.addServlet(context, "IndexServlet", IndexServlet.getInstance());

        context.addServletMappingDecoded("/", "default");
        context.addServletMappingDecoded("", "IndexServlet");
        context.addServletMappingDecoded("/message", "MessageServlet");
        context.addServletMappingDecoded("/user", "UserServlet");

        try
        {
            tomcat.start();
            tomcat.getConnector();
            tomcat.getServer().await();
        }
        catch (LifecycleException e)
        {
            e.printStackTrace();
        }
    }
}