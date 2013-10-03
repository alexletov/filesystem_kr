/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.entities.dao;

import java.util.ArrayList;
import java.util.List;
import ru.alexletov.fs.dto.FileInfoDTO;

/**
 *
 * @author Alex
 */
public interface FileDAO {
    
    public ArrayList<FileInfoDTO> getFilesByUserFolder(Integer id);
}
