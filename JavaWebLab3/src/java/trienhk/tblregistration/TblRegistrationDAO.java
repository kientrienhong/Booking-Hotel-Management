/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblregistration;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trienhk.services.EncryptionService;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblRegistrationDAO implements Serializable {

    public TblRegistrationDTO checkLogin(String email, String password)
            throws NamingException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblRegistrationDTO dto = null;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "SELECT name, roleId, phone "
                        + "FROM tblRegistration "
                        + "WHERE id = ? AND password = ? AND status = 'Active'";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                String pass = EncryptionService.toHexString(EncryptionService.getSHA(password));
                preStm.setString(2, pass);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String roleId = rs.getString("roleId");
                    String phone = rs.getString("phone");
                    dto = new TblRegistrationDTO(email, "***", name, roleId);
                    dto.setPhone(phone);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return dto;
    }

    public boolean signUp(TblRegistrationDTO dto)
            throws NamingException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT "
                        + "tblRegistration(id, password, name, roleId, status, address, phone, createdDate) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(3, dto.getName());
                preStm.setString(4, dto.getRoleId());
                preStm.setString(5, dto.getStatus());
                String pass = EncryptionService.toHexString(EncryptionService.getSHA(dto.getPassword()));
                preStm.setString(2, pass);
                preStm.setString(6, dto.getAddress());
                preStm.setString(7, dto.getPhone());
                preStm.setDate(8, dto.getCreatedDate());

                int row = preStm.executeUpdate();
                result = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }

    public boolean resetPassword(String newPassword, String txtUserId, String oldPassword)
            throws NamingException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "UPDATE "
                        + "tblRegistration SET password = ? "
                        + "WHERE id = ? AND password = ?";
                preStm = connection.prepareStatement(sql);
                String newPass = EncryptionService.toHexString(EncryptionService.getSHA(newPassword));
                String oldPass = EncryptionService.toHexString(EncryptionService.getSHA(oldPassword));
                preStm.setString(1, newPass);
                preStm.setString(2, txtUserId);
                preStm.setString(3, oldPass);

                int row = preStm.executeUpdate();
                result = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }
}
