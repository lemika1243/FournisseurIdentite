package model;

public class Etat{
    private int idEtat;
    private int etat;

    public int getIdEtat() {
        return idEtat;
    }
    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Etat(int idEtat, int etat){
        this.setIdEtat(idEtat);
        this.setEtat(etat);
    }

    public Etat(int etat){
        this.setEtat(etat);
    }

}