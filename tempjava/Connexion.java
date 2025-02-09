package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion{

    public static Connection dbConnect() throws Exception{
        Connection connection = null;
        try {
            // Class.forName("org.postgresql.Driver");
            // String url = "jdbc:postgresql://db:5432/fournisseur_identite";
            // String user = "postgres";
            // String password = "Tsiory24";
            // connection = DriverManager.getConnection(url, user, password);
             Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fournisseur_identite", "postgres", "Tsiory24");
            connection.setAutoCommit(false);
            System.out.println("Connexion etablie avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur de connexion dans dbConnect "+e.getMessage()+"   "+e.getLocalizedMessage());
        }
        return connection;
    }

}