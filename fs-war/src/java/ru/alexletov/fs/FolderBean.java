/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import ru.alexletov.fs.dto.FileDTO;
import ru.alexletov.fs.dto.UserDTO;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class FolderBean {
    @EJB
    private FileBean fb;
    
    @EJB
    private UserBean ub;
    
    private List<FileDTO> files;
    private List<UserDTO> users;
    
    private FileDTO selectedFile;
    @ManagedProperty("#{loginBean}")
    private LoginBean lb;
    
    @ManagedProperty("#{fileEditBean}")
    private FileEditBean feb;
    
    private Integer curFolder;
    
    private StreamedContent dlFile;
    
    private static final String destination = "\\files\\";
    /**
     * Creates a new instance of FileBean
     */
    public FolderBean() {
        users = new ArrayList<UserDTO>();
    }
    
    @PostConstruct
    private void init() {                  
        update();
    }
    
    public void update() {
        if(curFolder == null) {
            curFolder = lb.getId();
        }
        files = fb.getFiles(curFolder);
        List<FileDTO> toRemove= new ArrayList<FileDTO>();

        for(FileDTO f : files) {
            if((!lb.isAdmin()) &&
                    ((f.getShared() != 1) &&
                    (f.getOwner().getId() != lb.getId()))) {
                toRemove.add(f);
            }
        }
        files.removeAll(toRemove);
        
        UserDTO shared = new UserDTO();
        shared.setId(0);
        shared.setLogin("Shared");
        users.clear();
        users.add(shared);
        users.addAll(ub.getAllUsers());
    }
    
    public String editFile() {
        feb.setFileToEdit(selectedFile);
        return "file/edit.xhtml";
    }
    
    public String deleteFile() {
        String fname = System.getProperties().getProperty( "jboss.server.data.dir") +
                    destination + selectedFile.getPath() + "/" + selectedFile.getName();
        (new File(fname)).delete();
        fb.deleteFile(selectedFile);
        return "";
    }

    public StreamedContent getDlFile() {
        InputStream stream = null;
        try {
            String fname = System.getProperties().getProperty( "jboss.server.data.dir") +
                    destination + selectedFile.getPath();
            stream = new FileInputStream(new File(fname));
            dlFile = new DefaultStreamedContent(stream, selectedFile.getContentType(), selectedFile.getName());
            return dlFile;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FolderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean isEditFileCheckAccess(FileDTO file) {
        return file.getOwner().getId() == lb.getId() || lb.isAdmin();
    }
    
    public boolean checkFileDescription(String file) {
        if (file == null) {
            return true;
        }
        return file.length() == 0;
    }
    
    public String doLogOut() {
        curFolder = null;
        return lb.doLogOut();
    }
            
            
    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public FileDTO getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(FileDTO selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setLb(LoginBean lb) {
        this.lb = lb;
    }

    public Integer getCurFolder() {
        return curFolder;
    }

    public void setCurFolder(Integer curFolder) {
        this.curFolder = curFolder;
    }

    public FileEditBean getFeb() {
        return feb;
    }

    public void setFeb(FileEditBean feb) {
        this.feb = feb;
    }
    
}
