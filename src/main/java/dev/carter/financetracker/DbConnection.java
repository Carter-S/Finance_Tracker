package dev.carter.financetracker;
import java.sql.*;

public class DbConnection {
    private static final String URL = "jdbc:mysql://finance-tracker-db.ctxwsvc6ihts.eu-west-2.rds.amazonaws.com:3306/FinanceTracker";
    private static final String user = "admin";

    private String password;
    public Connection conn;

    public boolean attemptConnection(String password){
        try{
            //Get a connection to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, user, password);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}
