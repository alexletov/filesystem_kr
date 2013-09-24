/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ru.alexletov.fs.dto.FileInfoDTO;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class FileBean {
    private List<FileInfoDTO> files;
    private FileInfoDTO selectedFile;
    /**
     * Creates a new instance of FileBean
     */
    public FileBean() {
        files = new ArrayList<FileInfoDTO>();
        createTestFiles();
    }
    
    private void createTestFiles() {
        for(int i = 0; i < 100; i++) {
            FileInfoDTO fi = new FileInfoDTO();
            fi.setCreateDate(new Date());
            fi.setDescription("Descr");
            fi.setName("file" + Integer.toString(i)+ ".doc");
            fi.setOwner(null);
            fi.setShared(0);
            fi.setType(".doc");
            files.add(fi);
        }
    }
    
    public List<FileInfoDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoDTO> files) {
        this.files = files;
    }

    public FileInfoDTO getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(FileInfoDTO selectedFile) {
        this.selectedFile = selectedFile;
    }
    
    
}
