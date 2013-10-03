/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import ru.alexletov.fs.dto.FileInfoDTO;
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
    
    private List<FileInfoDTO> files;
    private List<UserDTO> users;
    
    private FileInfoDTO selectedFile;
    @ManagedProperty("#{loginBean}")
    private LoginBean lb;
    
    private Integer curFolder;
    /**
     * Creates a new instance of FileBean
     */
    public FolderBean() {
        users = new ArrayList<UserDTO>();
    }
    
    @PostConstruct
    private void init() {
        if(curFolder == null) {
            curFolder = lb.getId();
        }            
        files = fb.getFiles(curFolder);
        UserDTO shared = new UserDTO();
        shared.setId(0);
        shared.setLogin("Shared");
        users.add(shared);
        users.addAll(ub.getAllUsers());
    }
    
    public List<FileInfoDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoDTO> files) {
        this.files = files;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public FileInfoDTO getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(FileInfoDTO selectedFile) {
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
    
}
