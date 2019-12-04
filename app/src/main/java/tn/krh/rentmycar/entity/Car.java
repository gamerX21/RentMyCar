package tn.krh.rentmycar.entity;

public class Car {
    private int id;
    private String type;
    private String modele;
    private  int prix;
   private int agence_id;
   private String date_debut;
   private  String date_fin;
   private String Disponibilite;

    public Car(String type, String modele, int prix, int agence_id, String date_debut, String date_fin, String disponibilite) {
        this.type = type;
        this.modele = modele;
        this.prix = prix;
        this.agence_id = agence_id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        Disponibilite = disponibilite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(int agence_id) {
        this.agence_id = agence_id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getDisponibilite() {
        return Disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        Disponibilite = disponibilite;
    }
}
