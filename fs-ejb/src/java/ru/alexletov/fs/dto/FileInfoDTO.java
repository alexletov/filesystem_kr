/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alexletov.fs.dto;

import java.util.Date;
import ru.alexletov.fs.entities.File;

/**
 *
 * @author Alex
 */
public class FileInfoDTO {
    private Integer id;
    private String name;
    private Date createDate;
    private String description;
    private String type;
    private Integer shared;
    private UserDTO owner;

    public FileInfoDTO() {
    }
    
    public FileInfoDTO(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.createDate = file.getCreateDate();
        this.shared = file.getShared();
        this.description = file.getDescription().toString();
        this.type = makeType(this.name);
        this.owner = new UserDTO(file.getUserid());
    }
    
    private String makeType(String name) {
        String names[] = name.split(".");
        if(names.length > 0) {
            return names[names.length - 1];
        }
        return "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.type = makeType(this.name);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getShared() {
        return shared;
    }

    public void setShared(Integer shared) {
        this.shared = shared;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }
    
}
