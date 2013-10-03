/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.alexletov.fs.dto.FileInfoDTO;
import ru.alexletov.fs.entities.File;

/**
 *
 * @author Alex
 */
public class MySQLFileDAO implements FileDAO {
    protected EntityManager entityManager; 

    public MySQLFileDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ArrayList<FileInfoDTO> getFilesByUserFolder(Integer id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<File> criteria = builder.createQuery(File.class);

        Root<File> userRoot = criteria.from(File.class);
        criteria.select(userRoot);
        if(id != 0) {
            criteria.where(builder.equal(userRoot.get("userid"), id));
        }
        else {
            criteria.where(builder.equal(userRoot.get("shared"), 1));
        }
        List<File> files = null;
        try {
            files = entityManager.createQuery(criteria).getResultList();
        } catch (NoResultException ex) {
            Logger.getLogger(MySQLUserDAO.class.getName()).log(Level.INFO, null, ex);
            return new ArrayList<FileInfoDTO>();
        }
        
        ArrayList<FileInfoDTO> fid = new ArrayList<FileInfoDTO>();
        for(File f : files) {
            FileInfoDTO dto = new FileInfoDTO(f);
            fid.add(dto);
        }
        return fid;
    }
    
    
}
