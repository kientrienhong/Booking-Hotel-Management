/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblfeedback;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.tblinvoicedetail.TblInvoiceDetailDTO;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblFeedbackDAO implements Serializable {

    public TblFeedbackDTO getTblFeedbackDTO(String registrationId, int hotelId, String idRoomType)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblFeedbackDTO tblFeedbackDTO = null;
        try {
            conn = DBHelpers.getConnection();

            if (conn != null) {
                String sql = "SELECT rating "
                        + "FROM tblFeedback "
                        + "WHERE registrationId = ? AND hotelId = ? AND idRoomType = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, registrationId);
                preStm.setInt(2, hotelId);
                preStm.setString(3, idRoomType);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    int rating = rs.getInt("rating");
                    tblFeedbackDTO = new TblFeedbackDTO(registrationId, hotelId, idRoomType, rating, "Active");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return tblFeedbackDTO;
    }

    public boolean updateRating(TblFeedbackDTO dto)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();

            if (conn != null) {
                String sql = "UPDATE tblFeedback SET rating = ? "
                        + "WHERE registrationId = ? AND hotelId = ? AND idRoomType = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, dto.getRating());
                preStm.setString(2, dto.getRegistrationId());
                preStm.setInt(3, dto.getHotelId());
                preStm.setString(4, dto.getIdRoomType());

                check = preStm.executeUpdate() > 0;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return check;
    }

    public boolean insertFeedback(TblFeedbackDTO dto)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;

        boolean check = false;
        try {
            conn = DBHelpers.getConnection();

            if (conn != null) {
                String sql = "INSERT tblFeedback(registrationId, hotelId, idRoomType, rating, status) "
                        + "values(?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getRegistrationId());
                preStm.setString(3, dto.getIdRoomType());
                preStm.setInt(2, dto.getHotelId());
                preStm.setInt(4, dto.getRating());
                preStm.setString(5, dto.getStatus());
                check = preStm.executeUpdate() > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return check;
    }

    public List<TblFeedbackDTO> getRatingFromInvoiceDetail(List<TblInvoiceDetailDTO> list, String registrationId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblFeedbackDTO> feedbackDTOs = null;

        try {
            conn = DBHelpers.getConnection();

            if (conn != null) {
                String sql = "SELECT rating "
                        + "FROM tblFeedback "
                        + "WHERE registrationId = ? AND hotelId = ? AND idRoomType = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                
                feedbackDTOs = new ArrayList<>();
                
                for (TblInvoiceDetailDTO dto : list) {
                    preStm.setString(1, registrationId);
                    preStm.setInt(2, dto.getIdHotel());
                    preStm.setString(3, dto.getIdRoomType());

                    rs = preStm.executeQuery();

                    if (rs.next()) {
                        int rating = rs.getInt("rating");
                        TblFeedbackDTO tblFeedbackDTO = new TblFeedbackDTO(registrationId, dto.getIdHotel(), dto.getIdRoomType(), rating, "Active");
                        feedbackDTOs.add(tblFeedbackDTO);
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return feedbackDTOs;
    }
}
