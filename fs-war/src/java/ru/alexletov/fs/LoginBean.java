/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ru.alexletov.fs.dto.UserDTO;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class LoginBean {
    @EJB
    private AuthenticateBean authBean;
    @EJB
    private UserBean ub;
    private boolean logged = false;
    private boolean loginError = false;
    private String login;
    private String password;
    private String name;
    private String lastName;
    private Integer id;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    public boolean isLogged() {
        return logged;
    }
    
    public void doLogin() {
        loginError = false;
        logged = authBean.doLogin(login, password);
        if (!logged) {
            loginError = true;
            return;
        }
        this.password = "";
        UserDTO user = ub.getUserByLogin(login);
        this.name = user.getName();
        this.lastName = user.getLastname();
        this.id = user.getId();
    }
    
    public String doLogOut() {
        logged = false;
        loginError = false;
        login = "";
        password = "";
        name = "";
        lastName = "";
        id = 0;
        return "index";
    }       
    
    public void validateName(FacesContext context, UIComponent component,
            Object value) {
        String l = (String) value;
        if (l == null || l.length() < 5) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login is too small", "Login is too small"));
        }
    }
    
    public void validatePassword(FacesContext context, UIComponent component,
            Object value) {
        String p = (String)value;
        if (p == null || p.length() < 5) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Password is too small", "Password is too small"));
        }
    }

    public boolean isLoginError() {
        return loginError;
    }

    public void setLoginError(boolean loginError) {
        this.loginError = loginError;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
