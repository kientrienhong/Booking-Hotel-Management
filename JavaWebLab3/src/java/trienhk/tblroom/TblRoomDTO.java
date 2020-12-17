/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblroom;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblRoomDTO implements Serializable {

    private int idHotel;
    private int totalAmount;
    private String idRoomType;
    private double price;
    private String status;

    public TblRoomDTO(int idHotel, int totalAmount, String idRoomType, double price, String status) {
        this.idHotel = idHotel;
        this.totalAmount = totalAmount;
        this.idRoomType = idRoomType;
        this.price = price;
        this.status = status;
    }

    public TblRoomDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        this.idRoomType = idRoomType;
    }
}
