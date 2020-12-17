/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblrole;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblRoleDTO implements Serializable{
    private String id; 
    private String name; 

    public TblRoleDTO() {
    }

    public TblRoleDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
