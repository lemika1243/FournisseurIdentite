package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion{

    public static Connection dbConnect() throws Exception{
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/fournisseur_identite";
            String user = "postgres";
            String password = "Johary37";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion etablie avec succes !");
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return connection;
    }

}