package org.example.accounts;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String login;

    @Column(nullable = false)
    private String pass;

    @Column(nullable = false)
    private String email;

    public UserDataSet() {}

    public UserDataSet(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() { return login; }
    public String getPass() { return pass; }
    public String getEmail() { return email; }
}