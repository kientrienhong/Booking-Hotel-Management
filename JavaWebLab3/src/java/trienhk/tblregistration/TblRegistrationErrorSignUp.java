/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblregistration;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblRegistrationErrorSignUp implements Serializable {

    private String emailError;
    private String passwordError;
    private String passwordConfirmError;
    private String nameError;
    private String emailExisted;
    private String addressError;
    private String phoneError;

    public TblRegistrationErrorSignUp() {
    }

    public TblRegistrationErrorSignUp(String emailError, String passwordError, String passwordConfirmError, String nameError, String emailExisted) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.nameError = nameError;
        this.emailExisted = emailExisted;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordConfirmError() {
        return passwordConfirmError;
    }

    public void setPasswordConfirmError(String passwordConfirmError) {
        this.passwordConfirmError = passwordConfirmError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getEmailExisted() {
        return emailExisted;
    }

    public void setEmailExisted(String emailExisted) {
        this.emailExisted = emailExisted;
    }

}
