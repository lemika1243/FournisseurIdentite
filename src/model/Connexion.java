package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion{

    public static Connection dbConnect() throws Exception{
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/cloud";
            String user = "cloud";
            String password = " ";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion etablie avec succes !");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la connexion à la base de donnees : " + e.getMessage());
        }
        return connection;
    }

}