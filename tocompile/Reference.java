package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reference {
    private int id;
    private TypeReference type;
    private double duree; // Duration in appropriate units, e.g., seconds or minutes

    // Constructor
    public Reference(int id, TypeReference type, double duree2) {
            this.id = id;
            this.type = type;
            this.duree = duree2;
    }

    public Reference(int id , float duree , TypeReference type){
        this.setId(id);
        this.setDuree(duree);
        this.setType(type);
    }

    public Reference(float duree , TypeReference type){
        this.setDuree(duree);
        this.setType(type);
    }

    // Default Constructor
    public Reference() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeReference getType() {
        return type;
    }

    public void setType(TypeReference type) {
        this.type = type;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    // toString Method
    @Override
    public String toString() {
        return "ReferenceReinitialisation{" +
                "id=" + id +
                ", type=" + type +
                ", duree=" + duree +
                '}';
    }

   public static Reference[] getAll(Connection connection, TypeReference[] types) throws Exception {
        List<Reference> references = new ArrayList<>();
        String query = "SELECT * FROM reference";

        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int idReference = rs.getInt("id_reference");
                float duree = rs.getFloat("duree");
                int idType = rs.getInt("id_type");

                TypeReference typeReference = TypeReference.getById(idType, types);
                
                Reference reference = new Reference(idReference, duree, typeReference);
                references.add(reference);
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de getAll references : " + e.getMessage());
        }

        return references.toArray(new Reference[0]);
    }

    public static Reference getById(Connection connection, int id) throws SQLException {
        String query = """
            SELECT r.id_reference, r.duree, tr.id_type, tr.type
            FROM reference r
            INNER JOIN type_reference tr ON r.id_type = tr.id_type
            WHERE r.id_reference = ?
        """;

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Extract data from result set
            int idReference = rs.getInt("id_reference");
            double duree = rs.getDouble("duree");

            // Create TypeReference object
            int idType = rs.getInt("id_type");
            String type = rs.getString("type");
            TypeReference typeReference = new TypeReference(idType, type);

            // Create and return Reference object
            return new Reference(idReference, typeReference, duree);
        }

        // Return null if no result found
        return null;
    }

    public static Reference getTokenReference(Connection connection) throws Exception{
        return getById(connection, 2);
    }

    

    public static Reference getByType(Reference[] references , String type) throws Exception{
        Reference ref = null;
        String t = "";
        for (Reference reference : references) {
            t += reference.getType().getTypeReference() ;
            if(type.equals(reference.getType().getTypeReference())){
                ref = reference;
                break;
            }
        }
        // throw new Exception(t);
        return ref;
    }

}
