package model;

public class Utilisateur {
    private int idUtilisateur;
    private String email;
    private String mdp;
    private int tentativeMax;
    private Etat etat;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public int getTentativeMax() {
        return tentativeMax;
    }
    public void setTentativeMax(int tentativeMax) {
        this.tentativeMax = tentativeMax;
    }
    public Etat getEtat() {
        return etat;
    }
    public void setEtat(Etat etat) {
        this.etat = etat;
    }


    public Utilisateur(int idUtilisateur, String email, String mdp, int tentativeMax, Etat etat){
        this.setIdUtilisateur(idUtilisateur);
        this.setEmail(email);
        this.setMdp(mdp);
        this.setTentativeMax(tentativeMax);
        this.setEtat(etat);
    }
}
