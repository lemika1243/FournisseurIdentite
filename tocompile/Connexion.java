package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion{

    public static Connection dbConnect() {
        Connection connection = null;
        try {
            String url = "jdbc:postgresql://@db:5432/immobilier";
            String user = "postgres";
            String password = "Johary37";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion etablie avec succes !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de donnees : " + e.getMessage());
        }
        return connection;
    }

}