package model;

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
}
