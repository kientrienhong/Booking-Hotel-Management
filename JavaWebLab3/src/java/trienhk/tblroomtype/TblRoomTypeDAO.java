/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblroomtype;

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
public class TblRoomTypeDAO implements Serializable {

    public List<TblRoomTypeDTO> loadAllType()
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblRoomTypeDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT id, name "
                        + "From tblRoomType ";

                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                result = new ArrayList<>();

                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    TblRoomTypeDTO dto = new TblRoomTypeDTO(id, name);
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
}
