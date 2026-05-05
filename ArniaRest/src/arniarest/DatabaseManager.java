package arniarest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // AGGIUNTO IL PARAMETRO MAGICO ALLA FINE DELL'URL: &sessionVariables=foreign_key_checks=0
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/apicoltura?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&sessionVariables=foreign_key_checks=0";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = ""; 

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() { 
        return connection; 
    }
}