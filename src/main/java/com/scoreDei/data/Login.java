package com.scoreDei.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Login {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int login_id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Boolean is_admin;

    public Login() {
    }

    public Login(int login_id, String username, String password, String name, String email, String phone, Boolean is_admin) {
        this.login_id = login_id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.is_admin = is_admin;
    }

    public Login(String username, String password, String name, String email, String phone, Boolean is_admin) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.is_admin = is_admin;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getis_admin() {
        return is_admin;
    }

    public void setis_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }
}
