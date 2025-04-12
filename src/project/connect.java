package project;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;
public class connect
{
    public static void main(String[] args)
    {
        Connection conn=null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql://localhost/dentalemr?user=root&password=123456");
            System.out.println("connected...");
        }

        catch (Exception ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}

