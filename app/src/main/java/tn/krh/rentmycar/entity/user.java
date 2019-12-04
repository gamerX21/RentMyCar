package tn.krh.rentmycar.entity;

public class user {
    int id;
    String username;
    String First_name;
    String Second_name;
    String country;
    String City;
    String Adresse;

    public user(int id, String username, String first_name, String second_name, String country, String city, String adresse) {
        this.id = id;
        this.username = username;
        First_name = first_name;
        Second_name = second_name;
        this.country = country;
        City = city;
        Adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getSecond_name() {
        return Second_name;
    }

    public void setSecond_name(String second_name) {
        Second_name = second_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }
}
