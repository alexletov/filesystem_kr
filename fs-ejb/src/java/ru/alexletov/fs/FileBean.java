/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.alexletov.fs.dto.FileInfoDTO;
import ru.alexletov.fs.entities.dao.DAOFactory;
import ru.alexletov.fs.entities.dao.FileDAO;

/**
 *
 * @author Alex
 */
@Stateless
@LocalBean
public class FileBean {
    @PersistenceContext
    EntityManager em;
    
    public List<FileInfoDTO> getFiles(Integer userid) {
        DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL, em);
        FileDAO fd = df.getFileDAO();
        return fd.getFilesByUserFolder(userid);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    

}
