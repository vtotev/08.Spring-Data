package com.example.springdataautomappingobjects_exc.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String email;
    private String password;
    private String fullName;
    private Set<Game> games;
    private boolean isAdmin;
    private Set<Order> orders;

    public User() {
        games = new HashSet<>();
        orders = new HashSet<>();
    }

    public User(String email, String password, String fullName, boolean isAdmin) {
        setEmail(email);
        setPassword(password);
        setFullName(fullName);
        setAdmin(isAdmin);
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String mailPattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        Pattern pattern = Pattern.compile(mailPattern);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Incorrect email address");
        }
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String passwordPattern = "^([A-Z]{1,}|[a-z]{1,}|[0-9]{1,}){6,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        if (!pattern.matcher(password).find()) {
            throw new IllegalArgumentException("Incorrect password");
        }
        this.password = password;
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
