/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblroomtype;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblRoomTypeDTO implements Serializable {

    private String id;
    private String name;

    public TblRoomTypeDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TblRoomTypeDTO() {
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
