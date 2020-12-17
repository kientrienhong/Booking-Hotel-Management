/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblInvoice;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblInvoiceDAO implements Serializable {

    public List<String> getListAlreadyBookInvoiceBasedOnDate(Date checkInDate, Date checkOutDate) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id "
                        + "From tblInvoice "
                        + "Where checkInDate < ? AND checkOutDate > ? " 
                        + "OR checkOutDate > ? AND checkInDate < ? " 
                        + "OR checkInDate > ? AND checkOutDate < ? "
                        + "OR checkInDate < ? AND checkOutDate > ? " 
                        + "OR checkInDate = ? AND checkOutDate = ? ";

                preStm = conn.prepareStatement(sql);
                preStm.setDate(1, checkInDate);
                preStm.setDate(2, checkInDate);
                preStm.setDate(3, checkOutDate);
                preStm.setDate(4, checkOutDate);
                preStm.setDate(5, checkInDate);
                preStm.setDate(6, checkOutDate);
                preStm.setDate(7, checkInDate);
                preStm.setDate(8, checkOutDate);
                preStm.setDate(9, checkInDate);
                preStm.setDate(10, checkOutDate);

                result = new ArrayList<>();

                rs = preStm.executeQuery();
                while (rs.next()) {
                    String idHotel = rs.getString("id");
                    result.add(idHotel);
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

        return result;
    }

    public List<String> getListAlreadyBookBasedOnName() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> result = null;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id "
                        + "From tblInvoice "
                        + "Where checkInDate < ? AND checkOutDate > ? ";
                preStm = conn.prepareStatement(sql);
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                preStm.setTimestamp(1, ts);
                preStm.setTimestamp(2, ts);

                result = new ArrayList<>();

                rs = preStm.executeQuery();
                while (rs.next()) {
                    String idHotel = rs.getString("id");
                    result.add(idHotel);
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

        return result;
    }

    public Date findDateCheckOutById(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Date dateCheckOut = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT checkOutDate "
                        + "From tblInvoice "
                        + "Where id = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);

                rs = preStm.executeQuery();
                if (rs.next()) {
                    dateCheckOut = rs.getDate("checkOutDate");
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

        return dateCheckOut;
    }

    public List<TblInvoiceDTO> getByDateBooking(Date check, String userId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblInvoiceDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, checkInDate, checkOutDate, totalPrice, phone, recipientName, isConfirm "
                        + "From TblInvoice "
                        + "Where bookingDate = ? AND registrationId = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setDate(1, check);
                preStm.setString(2, userId);

                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("id");
                    Date checkInDate = rs.getDate("checkInDate");
                    Date checkOutDate = rs.getDate("checkOutDate");
                    double totalPrice = rs.getDouble("totalPrice");
                    String phone = rs.getString("phone");
                    String recipientName = rs.getString("recipientName");
                    boolean isConfirm = rs.getBoolean("isConfirm");
                    TblInvoiceDTO dto = new TblInvoiceDTO(id, userId, checkInDate, checkOutDate, totalPrice, check, "Active", phone, recipientName, isConfirm);

                    list.add(dto);
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

        return list;
    }

    public List<TblInvoiceDTO> getByHotelId(List<String> listIdInvoice, String userId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblInvoiceDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT ROW_NUMBER() OVER (ORDER BY bookingDate DESC) AS RowNr, checkInDate, checkOutDate, totalPrice, bookingDate, phone, recipientName, isConfirm "
                        + "From TblInvoice "
                        + "Where id = ? AND status = 'Active' AND registrationId = ? ";
                preStm = conn.prepareStatement(sql);
                list = new ArrayList<>();
                for (String idInvoice : listIdInvoice) {
                    preStm.setString(1, idInvoice);
                    preStm.setString(2, userId);
                    rs = preStm.executeQuery();
                    if (rs.next()) {
                        Date checkInDate = rs.getDate("checkInDate");
                        Date checkOutDate = rs.getDate("checkOutDate");
                        Date bookingDate = rs.getDate("bookingDate");
                        double totalPrice = rs.getDouble("totalPrice");
                        String phone = rs.getString("phone");
                        String recipientName = rs.getString("recipientName");
                        boolean isConfirm = rs.getBoolean("isConfirm");
                        TblInvoiceDTO dto = new TblInvoiceDTO(idInvoice, userId, checkInDate, checkOutDate, totalPrice, bookingDate, "Active", phone, recipientName, isConfirm);
                        list.add(dto);
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

        return list;
    }

    public List<TblInvoiceDTO> getByHotelIdAndBookingDate(List<String> listIdInvoice, String userId, Date bookingDate)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblInvoiceDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT ROW_NUMBER() OVER (ORDER BY bookingDate DESC) AS RowNr, checkInDate, checkOutDate, totalPrice, phone, recipientName, isConfirm "
                        + "From TblInvoice "
                        + "Where id = ? AND status = 'Active' AND registrationId = ? AND bookingDate = ?";
                preStm = conn.prepareStatement(sql);
                list = new ArrayList<>();
                for (String idInvoice : listIdInvoice) {
                    preStm.setString(1, idInvoice);
                    preStm.setString(2, userId);
                    preStm.setDate(3, bookingDate);
                    rs = preStm.executeQuery();
                    if (rs.next()) {
                        Date checkInDate = rs.getDate("checkInDate");
                        Date checkOutDate = rs.getDate("checkOutDate");
                        double totalPrice = rs.getDouble("totalPrice");
                        String phone = rs.getString("phone");
                        String recipientName = rs.getString("recipientName");
                        boolean isConfirm = rs.getBoolean("isConfirm");
                        TblInvoiceDTO dto = new TblInvoiceDTO(idInvoice, userId, checkInDate, checkOutDate, totalPrice, bookingDate, "Active", phone, recipientName, isConfirm);
                        list.add(dto);
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

        return list;
    }

    public List<String> getByRegistrationId(String id)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id "
                        + "From TblInvoice "
                        + "Where registrationId = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);
                rs = preStm.executeQuery();
                list = new ArrayList<>();

                while (rs.next()) {
                    String invoiceId = rs.getString("id");
                    list.add(invoiceId);
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
        return list;
    }

    public boolean deleteBooking(String id, String userId, Timestamp currentTime)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update TblInvoice SET status = 'Deative'"
                        + "Where id = ? AND registrationId = ? AND checkOutDate < ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);
                preStm.setString(2, userId);
                preStm.setTimestamp(3, currentTime);
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

    public boolean insert(TblInvoiceDTO dto, Connection conn)
            throws SQLException {
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            if (conn != null) {
                String sql = "INSERT TblInvoice(id, registrationId, checkInDate, checkOutDate, totalPrice, status, bookingDate, phone, recipientName, isConfirm) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(2, dto.getRegistrationId());
                preStm.setDate(3, dto.getCheckInDate());
                preStm.setDate(4, dto.getCheckOutDate());
                preStm.setDouble(5, dto.getTotalPrice());
                preStm.setString(6, dto.getStatus());
                preStm.setDate(7, dto.getDateBooking());
                preStm.setString(8, dto.getPhone());
                preStm.setString(9, dto.getRecipientName());
                preStm.setBoolean(10, dto.isIsConfirm());
                check = preStm.executeUpdate() > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
        }

        return check;
    }

    public boolean confirmBooking(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update TblInvoice SET isConfirm = ? "
                        + "Where id = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setBoolean(1, true);
                preStm.setString(2, id);
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
}
