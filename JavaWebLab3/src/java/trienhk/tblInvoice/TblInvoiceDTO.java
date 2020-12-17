/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblInvoice;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Treater
 */
public class TblInvoiceDTO implements Serializable {

    private String id;
    private String registrationId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String status;
    private Date dateBooking;
    private String phone;
    private String recipientName;
    private boolean isConfirm;
    
    public TblInvoiceDTO(String id, String registrationId, Date checkInDate, Date checkOutDate, double totalPrice, Date dateBooking, String status, String phone, String recipientName, boolean isConfirm) {
        this.id = id;
        this.registrationId = registrationId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.dateBooking = dateBooking;
        this.status = status;
        this.phone = phone;
        this.recipientName = recipientName;
        this.isConfirm = isConfirm;
    }

    public TblInvoiceDTO() {
    }

    public boolean isIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(boolean isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(Date dateBooking) {
        this.dateBooking = dateBooking;
    }

}
