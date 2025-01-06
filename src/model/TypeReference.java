package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TypeReference {
    private int idTypePreference;
    private String typeReference;

    public int getIdTypePreference() {
        return idTypePreference;
    }
    public void setIdTypePreference(int idTypePreference) {
        this.idTypePreference = idTypePreference;
    }
    public String getTypeReference() {
        return typeReference;
    }
    public void setTypeReference(String typeReference) {
        this.typeReference = typeReference;
    }

    public TypeReference(int idTypePreference, String typeReference){
        this.setIdTypePreference(idTypePreference);
        this.setTypeReference(typeReference);
    }

    public TypeReference(String typeReference){
        this.setTypeReference(typeReference);
    }



    public static TypeReference[] getAll(Connection connection)throws Exception{
        List<TypeReference> types = new ArrayList<>();
        String query = "select * from type_reference";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    TypeReference type = new TypeReference(rs.getInt("id_type"),rs.getString("type"));
                    types.add(type);
                }
            } 
        } catch (Exception e) {
            throw new Exception("erreur lors de getAll type Reference :"+e.getMessage());
        }
        return types.toArray(new TypeReference[0]);
    }

    public static TypeReference getById(Connection connection, int idTypePreference) throws Exception {
        TypeReference typeReference = null;
        String query = "SELECT * FROM type_reference WHERE id_type = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idTypePreference);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_type");
                    String type = rs.getString("type");

                    typeReference = new TypeReference(id, type);
                }
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du type de référence par ID : " + e.getMessage());
        }

        return typeReference;
    }

    public static TypeReference getById(int id,TypeReference[] all){
        TypeReference type = null;
        for (TypeReference typeReference : all) {
            if(typeReference.getIdTypePreference() == id){
                type = typeReference;
                break;
            }
        }
        return type;
    }
}
