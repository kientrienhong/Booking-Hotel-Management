/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblinvoicedetail;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblInvoiceDetailDTO implements Serializable {

    private String invoiceId;
    private String idRoomType;
    private int quantity;
    private int idHotel;
    private double price;
    private String status;

    public TblInvoiceDetailDTO(String invoiceId, String idRoomType, int quantity, int idHotel, double price, String status) {
        this.invoiceId = invoiceId;
        this.idRoomType = idRoomType;
        this.quantity = quantity;
        this.idHotel = idHotel;
        this.price = price;
        this.status = status;
    }

    public TblInvoiceDetailDTO(String invoiceId, String idRoomType, int idHotel) {
        this.invoiceId = invoiceId;
        this.idRoomType = idRoomType;
        this.idHotel = idHotel;
    }

    public TblInvoiceDetailDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        this.idRoomType = idRoomType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
