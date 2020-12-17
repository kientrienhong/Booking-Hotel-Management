/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblfeedback;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblFeedbackDTO implements Serializable {

    private String registrationId;
    private int hotelId;
    private String idRoomType;
    private int rating;
    private String status;

    public TblFeedbackDTO(String registrationId, int hotelId, String idRoomType, int rating, String status) {
        this.registrationId = registrationId;
        this.hotelId = hotelId;
        this.idRoomType = idRoomType;
        this.rating = rating;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        this.idRoomType = idRoomType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
