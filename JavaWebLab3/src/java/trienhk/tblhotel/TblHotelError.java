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
public class TblHotelError implements Serializable {

    private String nameError;
    private String areaError;
    private String emptyError;
    private String checkInError;
    private String checkOutError;
    private String invalidDateError;
    private String amountError;

    public TblHotelError() {
    }

    public String getAmountError() {
        return amountError;
    }

    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getAreaError() {
        return areaError;
    }

    public void setAreaError(String areaError) {
        this.areaError = areaError;
    }

    public String getEmptyError() {
        return emptyError;
    }

    public void setEmptyError(String emptyError) {
        this.emptyError = emptyError;
    }

    public String getCheckInError() {
        return checkInError;
    }

    public void setCheckInError(String checkInError) {
        this.checkInError = checkInError;
    }

    public String getCheckOutError() {
        return checkOutError;
    }

    public void setCheckOutError(String checkOutError) {
        this.checkOutError = checkOutError;
    }

    public String getInvalidDateError() {
        return invalidDateError;
    }

    public void setInvalidDateError(String invalidDateError) {
        this.invalidDateError = invalidDateError;
    }

}
