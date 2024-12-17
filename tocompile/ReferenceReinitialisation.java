package model;


public class ReferenceReinitialisation {
    private int id;
    private TypeReference type;
    private int duree; // Duration in appropriate units, e.g., seconds or minutes

    // Constructor
    public ReferenceReinitialisation(int id, TypeReference type, int duree) {
        this.id = id;
        this.type = type;
        this.duree = duree;
    }

    // Default Constructor
    public ReferenceReinitialisation() {}

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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
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
}
