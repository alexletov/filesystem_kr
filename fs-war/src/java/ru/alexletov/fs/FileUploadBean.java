/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;
import ru.alexletov.fs.dto.FileDTO;
import ru.alexletov.fs.dto.UserDTO;

/**
 *
 * @author Alex
 */
@ManagedBean
@RequestScoped
public class FileUploadBean {
    @EJB
    FileBean fb;
    
    @ManagedProperty("#{loginBean}")
    private LoginBean lb;
    
    private UploadedFile file;
    private String description;
    private boolean shared;
    
    private static final String destination = "\\files\\";
    /**
     * Creates a new instance of FileUploadBean
     */
    public FileUploadBean() {
    }
    
    public String upload() {  
        if(file != null) {
            try {
                FileDTO f = new FileDTO();
                f.setPath(copyFile(file.getInputstream()));
                if(f.getPath().equals("")) {
                    return "error";
                }
                f.setName(file.getFileName());                
                f.setDescription(description);
                if (shared) {
                    f.setShared(1);
                }
                else {
                    f.setShared(0);
                }
                UserDTO u = new UserDTO();
                u.setId(lb.getId());
                f.setOwner(u);
                
                fb.addFile(f);
            } catch (IOException ex) {
                Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
                return "error";
            }
        }  
        return "success";
    }  
    
    private String copyFile(InputStream in) {
        String dir = System.getProperties().getProperty( "jboss.server.data.dir") + 
                destination;
        String fname = Long.toString(System.currentTimeMillis()) + "_" +
                Long.toString((new Random()).nextLong()) + ".dbf";
        try {            
            OutputStream out = new FileOutputStream(new File(dir + fname));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
        return fname;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public LoginBean getLb() {
        return lb;
    }

    public void setLb(LoginBean lb) {
        this.lb = lb;
    }
    
}
