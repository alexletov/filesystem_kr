/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.alexletov.fs.dto.UserDTO;
import ru.alexletov.fs.entities.User;

/**
 *
 * @author Alex
 */
public class MySQLUserDAO implements UserDAO {
    protected EntityManager entityManager; 

    public MySQLUserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public boolean checkPasswordByLogin(String login, String password) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);

        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);
        criteria.where(builder.equal(userRoot.get("login"), login));
        
        User user;
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            return false;
        }
        
        if (user == null) {
            return false;
        }
        
        String pwdEncrypted;
        try {
            pwdEncrypted = encryptPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if (user.getPassword().equals(pwdEncrypted)) {
            return true;
        }        
        return false;
    }

    @Override
    public boolean checkPasswordById(Long id, String password) {     
        User user = entityManager.find(User.class, id);

        if (user == null) {
            return false;
        }
        String pwdEncrypted;
        try {
            pwdEncrypted = encryptPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if (user.getPassword().equals(pwdEncrypted)) {
            return true;
        }        
        return false;
    }
    
    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public UserDTO addNewUser(String login, String password, String name,
            String lastName, String email) {
        User user = new User();
        user.setAdmin(0);
        user.setLogin(login);
        try {
            user.setPassword(encryptPassword(password));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        user.setName(name);
        user.setLastname(lastName);
        user.setEmail(email);
        try {
            entityManager.persist(user);
        } catch (PersistenceException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return new UserDTO(user);
    }
    
    @Override
    public UserDTO addNewUser(UserDTO user, String password) {
        return addNewUser(user.getLogin(), password, user.getName(),
                user.getLastname(), user.getEmail());
    }

    @Override
    public boolean userExists(String login) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);

        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);
        criteria.where(builder.equal(userRoot.get("login"), login));
        
        User user;
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.INFO, null, ex);
            return false;
        }
        
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);

        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);
        criteria.where(builder.equal(userRoot.get("login"), login));
        
        User user;
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.INFO, null, ex);
            return null;
        }
        
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();
        for(User u : (List<User>)query.getResultList())
        {
            users.add(new UserDTO(u));
        }
        return users;
    }
}
