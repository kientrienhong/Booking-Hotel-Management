/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.services;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.tblroomtype.TblRoomTypeDTO;
import trienhk.tblhotel.TblHotelDTO;
import trienhk.tblroom.TblRoomDAO;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
public class ConvertService implements Serializable {

    private Logger logger = null;

    private Map<TblHotelDTO, List<TblRoomDTO>> map;

    public ConvertService() {
        logger = Logger.getLogger(ConvertService.class.getName());
        BasicConfigurator.configure();
    }

    public Map<TblHotelDTO, List<TblRoomDTO>> convertListToMap(List<TblHotelDTO> list) {
        map = new HashMap<>();
        TblRoomDAO tblRoomDAO = new TblRoomDAO();
        try {
            for (int i = 0; i < list.size(); i++) {
                TblHotelDTO tblHotelDTO = list.get(i);
                List<TblRoomDTO> listTblRoomDTO = tblRoomDAO.findByHotelId(tblHotelDTO.getId());
                map.put(tblHotelDTO, listTblRoomDTO);
            }
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return map;
    }

    public Map<String, String> convertListRoomTypeToMap(List<TblRoomTypeDTO> list) {
        Map<String, String> result = new HashMap<>();
        if (list != null) {
            for (TblRoomTypeDTO dto : list) {
                result.put(dto.getId(), dto.getName());
            }
        }
        return result;
    }
}
