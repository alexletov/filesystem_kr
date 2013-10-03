/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.alexletov.fs.dto.UserDTO;
import ru.alexletov.fs.entities.dao.DAOFactory;
import ru.alexletov.fs.entities.dao.UserDAO;

/**
 *
 * @author Alex
 */
@Stateless
@LocalBean
public class UserBean {
    @PersistenceContext
    EntityManager em;
    
    public UserDTO addUser(UserDTO user, String password) {
        DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL, em);
        UserDAO ud = df.getUserDAO();
        return ud.addNewUser(user, password);
    }
    
    public boolean exists(String login) {
        DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL, em);
        UserDAO ud = df.getUserDAO();
        return ud.userExists(login);
    }
    
    public UserDTO getUserByLogin(String login) {
        DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL, em);
        UserDAO ud = df.getUserDAO();
        return ud.getUserByLogin(login);
    }
    
    public List<UserDTO> getAllUsers() {
        DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL, em);
        UserDAO ud = df.getUserDAO();
        return ud.getAllUsers();
        
    }
}
