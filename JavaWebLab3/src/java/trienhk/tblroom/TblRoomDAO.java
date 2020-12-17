/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblroom;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblRoomDAO implements Serializable {

    public List<TblRoomDTO> findByHotelId(int id) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblRoomDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT idRoomType, totalAmount, price "
                        + "From tblRoom "
                        + "Where idHotel = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    String type = rs.getString("idRoomType");
                    int totalAmount = rs.getInt("totalAmount");
                    double price = rs.getDouble("price");

                    TblRoomDTO dto = new TblRoomDTO(id, totalAmount, type, price, "Active");
                    result.add(dto);
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

    public TblRoomDTO getRoom(int idHotel, String idType) 
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblRoomDTO result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT totalAmount, price "
                        + "From tblRoom "
                        + "Where idHotel = ? AND idRoomType = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, idHotel);
                preStm.setString(2, idType);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    int amount = rs.getInt("totalAmount");
                    double price = rs.getDouble("price"); 
                    
                    result = new TblRoomDTO(idHotel, amount, idType, price, "Active");
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
}
