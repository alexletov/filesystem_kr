/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import javax.persistence.EntityManager;


/**
 *
 * @author Alex
 */
public class MySQLDAOFactory extends DAOFactory {
    EntityManager em;

    public MySQLDAOFactory(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO(em);
    }

    @Override
    public FileDAO getFileDAO() {
        return new MySQLFileDAO(em);
    }
    
}
