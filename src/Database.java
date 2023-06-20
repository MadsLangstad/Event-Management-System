import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    //# Fields
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    static{
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //# Constructors
    public Database(String user, String password) {
        this.host = "localhost";
        this.port = "3306";
        this.user = user;
        this.password = password;
    }

    public Database(String user, String password, String database) {
        this.host = "localhost";
        this.port = "3306";
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public Database(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }


    //# Methods
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://%s:%s".formatted(
                    this.host,
                    this.port
            ), this.user, this.password);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
