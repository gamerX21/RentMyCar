package tn.krh.rentmycar.entity;

import java.util.ArrayList;
import java.util.List;

public class Agence {
    private int id;
    private String name;
    private String adresse;
    private List<Car> cars = new ArrayList<>();
    private  String passwprd;

    public Agence(String name, String adresse, List<Car> cars) {
        this.name = name;
        this.adresse = adresse;
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswprd() {
        return passwprd;
    }

    public void setPasswprd(String passwprd) {
        this.passwprd = passwprd;
    }
}
