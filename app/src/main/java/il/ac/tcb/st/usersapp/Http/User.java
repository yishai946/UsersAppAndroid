package il.ac.tcb.st.usersapp.Http;

import java.util.List;

import il.ac.tcb.st.usersapp.MyObjects.Root;

public class User {
    public String id;
    public String firstname;
    public String lastname;
    public String email;
    public double age;
    public String city;
    public String country;
    public String picture;

    public User(String id, String firstname, String lastname, String email, double age, String city, String country, String picture) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.city = city;
        this.country = country;
        this.picture = picture;
    }

    public User() {
    }
}