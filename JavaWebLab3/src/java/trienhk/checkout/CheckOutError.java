/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.checkout;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class CheckOutError implements Serializable {

    private String dateError;
    private String checkInDateError;
    private String checkOutDateError;
    private String nameError;
    private String phoneError;
    private String outOfStockError;

    public CheckOutError(String dateError, String checkInDateError, String checkOutDateError, String nameError, String phoneError) {
        this.dateError = dateError;
        this.checkInDateError = checkInDateError;
        this.checkOutDateError = checkOutDateError;
        this.nameError = nameError;
        this.phoneError = phoneError;
    }

    public CheckOutError() {
    }

    public String getOutOfStockError() {
        return outOfStockError;
    }

    public void setOutOfStockError(String outOfStockError) {
        this.outOfStockError = outOfStockError;
    }

    public String getDateError() {
        return dateError;
    }

    public void setDateError(String dateError) {
        this.dateError = dateError;
    }

    public String getCheckInDateError() {
        return checkInDateError;
    }

    public void setCheckInDateError(String checkInDateError) {
        this.checkInDateError = checkInDateError;
    }

    public String getCheckOutDateError() {
        return checkOutDateError;
    }

    public void setCheckOutDateError(String checkOutDateError) {
        this.checkOutDateError = checkOutDateError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

}
