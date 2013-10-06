/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import java.util.ArrayList;
import ru.alexletov.fs.dto.FileDTO;

/**
 *
 * @author Alex
 */
public interface FileDAO {
    
    public ArrayList<FileDTO> getFilesByUserFolder(Integer id);
    public void addFile(FileDTO file);
}
