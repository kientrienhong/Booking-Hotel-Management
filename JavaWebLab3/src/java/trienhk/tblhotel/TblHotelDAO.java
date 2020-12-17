/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblhotel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import trienhk.cart.CartObject;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblHotelDAO implements Serializable {

    public String getNameById(int id) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "From tblHotel "
                        + "Where id = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    result = rs.getString(1);
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

    public List<TblHotelDTO> findByLikeName(String name)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where name LIKE ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String area = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, area, totalRoom, "Active");
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

    public List<TblHotelDTO> findByLikeNameAdmin(String name)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where name LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String area = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, area, totalRoom, "Active");
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

    public List<TblHotelDTO> findByLikeArea(String area)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where area LIKE ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + area + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String areaHotel = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, areaHotel, totalRoom, "Active");
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

    public List<TblHotelDTO> findByLikeAreaAdmin(String area)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where area LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + area + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String areaHotel = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, areaHotel, totalRoom, "Active");
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

    public List<TblHotelDTO> findByLikeAreaAndName(String area, String name)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where area LIKE ? AND name LIKE ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + area + "%");
                preStm.setString(2, "%" + name + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String areaHotel = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, areaHotel, totalRoom, "Active");
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

    public List<TblHotelDTO> findByLikeAreaAndNameAdmin(String area, String name)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblHotelDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, area, name, totalRoom "
                        + "From tblHotel "
                        + "Where area LIKE ? AND name LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + area + "%");
                preStm.setString(2, "%" + name + "%");

                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String areaHotel = rs.getString("area");
                    String nameHotel = rs.getString("name");
                    int totalRoom = rs.getInt("totalRoom");
                    TblHotelDTO dto = new TblHotelDTO(id, nameHotel, areaHotel, totalRoom, "Active");
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

    public Map<Integer, String> getNameOfHotel(List<Integer> listId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Map<Integer, String> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "From tblHotel "
                        + "Where id = ? AND status = 'Active'";
                preStm = conn.prepareStatement(sql);
                result = new HashMap<>();

                for (int i = 0; i < listId.size(); i++) {
                    preStm.setInt(1, listId.get(i));
                    rs = preStm.executeQuery();
                    if (rs.next()) {
                        String name = rs.getString("name");
                        result.put(listId.get(i), name);
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

        return result;
    }

    public Map<String, String> getNameOfHotelFromCart(CartObject cartObject)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Map<String, String> result = null;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                if (cartObject != null) {
                    String sql = "SELECT name "
                            + "From tblHotel "
                            + "Where id = ? AND status = 'Active'";
                    preStm = conn.prepareStatement(sql);
                    result = new HashMap<>();
                    for (String idItem : cartObject.getItems().keySet()) {
                        String idHotel = idItem.split("_")[0];

                        preStm.setInt(1, Integer.parseInt(idHotel));
                        rs = preStm.executeQuery();

                        if (rs.next()) {
                            String name = rs.getString("name");
                            result.put(idHotel, name);
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

        return result;
    }
}
