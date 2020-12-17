/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblhotel;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblHotelDTO implements Serializable{
    private int id; 
    private String name; 
    private String area; 
    private int totalRoom;
    private String status;
    
    public TblHotelDTO(int id, String name, String area, int totalRoom, String status) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.totalRoom = totalRoom;
        this.status = status;
    }

    public TblHotelDTO(int id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TblHotelDTO) {
            return this.getId() == ((TblHotelDTO) obj).getId();
        }
        
        return false;
    }
    
    
    public TblHotelDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(int totalRoom) {
        this.totalRoom = totalRoom;
    }
    
}
