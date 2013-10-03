/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import java.util.Collection;
import java.util.List;
import ru.alexletov.fs.dto.UserDTO;

/**
 *
 * @author Alex
 */
public interface UserDAO {
    
    /**
     * Check password for equals in database by login. Use SHA-512 encryption
     * @param login Login
     * @param password Password (unencrypted)
     * @return true if password equals, false if not.
     */
    public boolean checkPasswordByLogin(String login, String password);
    /**
     * Check password for equals in database by user id. Use SHA-512 encryption
     * @param id User id
     * @param password Password (unencrypted)
     * @return true if password equals, false if not.
     */
    public boolean checkPasswordById(Long id, String password);
    
    /**
     * Add new user to database.
     * @param login Login
     * @param password Password (unencrypted)
     * @param name Name
     * @param lastName Last name
     * @param email E-mail
     * @return User on success, null on fail
     */
    public UserDTO addNewUser(String login, String password, String name,
            String lastName, String email);
    
    public UserDTO addNewUser(UserDTO user, String password);
    
    public boolean userExists(String login);
    
    public UserDTO getUserByLogin(String login);
    
    public List<UserDTO> getAllUsers();
}
