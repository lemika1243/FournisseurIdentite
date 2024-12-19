package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Reference {
    int idreference;
    float duree;
    TypeReference typeReference;
    public int getIdreference() {
        return idreference;
    }
    public void setIdreference(int idreference) {
        this.idreference = idreference;
    }
    public float getDuree() {
        return duree;
    }
    public void setDuree(float duree) {
        this.duree = duree;
    }
    public TypeReference getTypeReference() {
        return typeReference;
    }
    public void setTypeReference(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    public Reference(int id , float duree , TypeReference type){
        this.setIdreference(id);
        this.setDuree(duree);
        this.setTypeReference(type);
    }

    public Reference(float duree , TypeReference type){
        this.setDuree(duree);
        this.setTypeReference(type);
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

    public static Reference getById(Reference[] references , int id){
        Reference ref = null;
        for (Reference reference : references) {
            if(id == reference.getIdreference()){
                ref = reference;
                break;
            }
        }
        return ref;
    }

    public static Reference getById(Connection connection, int idReference) throws Exception {
        Reference reference = null;
        String query = "SELECT r.id_reference, r.duree, t.id_type, t.type " +
                       "FROM reference r " +
                       "JOIN type_reference t ON r.id_type = t.id_type " +
                       "WHERE r.id_reference = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idReference);  // Lier l'ID de la référence passé en paramètre

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Récupérer les informations de la table 'reference'
                    int id = rs.getInt("id_reference");
                    float duree = rs.getFloat("duree");

                    // Récupérer les informations de la table 'type_reference'
                    int idType = rs.getInt("id_type");

                    // Créer l'objet TypeReference
                    TypeReference typeReference = new TypeReference(idType,rs.getString("type"));

                    // Créer l'objet Reference avec les données récupérées
                    reference = new Reference(id, duree, typeReference);
                }
            }
        }
        catch(Exception e){
            throw new Exception("erreur lors de getById Reference :"+e.getMessage());
        }

        return reference;  // Retourne l'objet Reference ou null si non trouvé
    }

    public static Reference getByType(Reference[] references , String type){
        Reference ref = null;
        for (Reference reference : references) {
            if(type.equalsIgnoreCase(reference.getTypeReference().getTypeReference())){
                ref = reference;
                break;
            }
        }
        return ref;
    }

    public static Reference getTokenReference(Connection connection) throws Exception{
        return getById(connection, 2);
    }

}
