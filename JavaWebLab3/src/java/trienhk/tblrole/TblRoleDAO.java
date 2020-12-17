/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblrole;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblRoleDAO implements Serializable {

    public String getNameById(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement preStm = null;
        String result = "failed";

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "From tblRole "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    result = rs.getString("name");
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
