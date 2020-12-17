/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblinvoicedetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import trienhk.tblhotel.TblHotelDTO;
import trienhk.tblroom.TblRoomDTO;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblInvoiceDetailDAO implements Serializable {

    public Map<TblHotelDTO, List<TblRoomDTO>> getSuitableHotel(List<String> idInvoice, Map<TblHotelDTO, List<TblRoomDTO>> map, int amount)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.getConnection();

            if (conn != null) {
                String sql = "SELECT quantity, idHotel, idRoomType "
                        + "From tblInvoiceDetail "
                        + "Where invoiceId = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                for (int i = 0; i < idInvoice.size(); i++) {
                    preStm.setString(1, idInvoice.get(i));
                    rs = preStm.executeQuery();
                    while (rs.next()) {
                        int quantity = rs.getInt("quantity");
                        String roomType = rs.getString("idRoomType");
                        TblHotelDTO hotelDTO = new TblHotelDTO(rs.getInt("idHotel"));
                        int totalAmountRoomnHotel = 0;
                        List<TblRoomDTO> list = map.get(hotelDTO);
                        if (list != null) {
                            for (int j = 0; j < list.size(); j++) {
                                if (roomType.equals(list.get(j).getIdRoomType())) {
                                    int totalAmountEachTypeRoom = map.get(hotelDTO).get(j).getTotalAmount();
                                    list.get(j).setTotalAmount(totalAmountEachTypeRoom - quantity);
                                }

                                if (list.get(j).getTotalAmount() <= 0) {
                                    list.remove(j);
                                }

                                totalAmountRoomnHotel += list.get(j).getTotalAmount();

                                if (list.isEmpty()) {
                                    map.remove(hotelDTO);
                                }
                            }

                            if (totalAmountRoomnHotel < amount) {
                                map.remove(hotelDTO);
                            }
                        }
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

        return map;
    }

    public TblRoomDTO getQuantityAvailableRoom(List<String> listInvoice, TblRoomDTO dto)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT quantity "
                        + "From tblInvoiceDetail "
                        + "Where invoiceId = ? AND idHotel = ? AND idRoomType = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                for (String invoice : listInvoice) {
                    preStm.setString(1, invoice);
                    preStm.setInt(2, dto.getIdHotel());
                    preStm.setString(3, dto.getIdRoomType());

                    rs = preStm.executeQuery();

                    if (rs.next()) {
                        int quantity = dto.getTotalAmount();
                        dto.setTotalAmount(quantity - rs.getInt("quantity"));
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

        return dto;
    }

    public List<String> getByHotelId(List<TblHotelDTO> listHotel)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT invoiceId "
                        + "From TblInvoiceDetail "
                        + "Where idHotel = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                list = new ArrayList<>();
                for (TblHotelDTO hotelDTO : listHotel) {
                    preStm.setInt(1, hotelDTO.getId());
                    rs = preStm.executeQuery();
                    while (rs.next()) {
                        String id = rs.getString("invoiceId");
                        list.add(id);
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

    public List<TblInvoiceDetailDTO> findByListIdInvoice(List<String> idInvoice)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblInvoiceDetailDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT idRoomType, idHotel "
                        + "From TblInvoiceDetail "
                        + "Where invoiceId = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                list = new ArrayList<>();
                for (String id : idInvoice) {
                    preStm.setString(1, id);
                    rs = preStm.executeQuery();
                    while (rs.next()) {
                        String idRoomType = rs.getString("idRoomType");
                        int idHotel = rs.getInt("idHotel");
                        TblInvoiceDetailDTO dto = new TblInvoiceDetailDTO(id, idRoomType, idHotel);
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

    public boolean insert(List<TblInvoiceDetailDTO> list, Connection conn) 
            throws SQLException {
        PreparedStatement preStm = null;
        boolean check = true;
        try {
            if (conn != null) {
                String sql = "INSERT TblInvoiceDetail(invoiceId, idRoomType, quantity, idHotel, price, status) "
                        + "values(?, ?, ?, ?, ?, ?) ";
                preStm = conn.prepareStatement(sql);
                
                for (TblInvoiceDetailDTO dto: list){
                    preStm.setString(1, dto.getInvoiceId());
                    preStm.setString(2, dto.getIdRoomType());
                    preStm.setInt(3, dto.getQuantity());
                    preStm.setInt(4, dto.getIdHotel());
                    preStm.setDouble(5, dto.getPrice());
                    preStm.setString(6, dto.getStatus());
                    preStm.addBatch();
                }
                
                int[] result = preStm.executeBatch();
                for (int i = 0; i < result.length; i++){
                    if (result[i] != 1){
                        check = false;
                    }
                }
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
        }

        return check;
    }
}
