/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.dto;

import java.io.Serializable;
import ru.alexletov.fs.entities.User;

/**
 *
 * @author Alex
 */
public class UserDTO implements Serializable {
    private Integer id;
    private String login;
    private String name;
    private String lastname;
    private String email;
    private Integer admin;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.admin = user.getAdmin();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.id = user.getId();
    }
            
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }
    
}
