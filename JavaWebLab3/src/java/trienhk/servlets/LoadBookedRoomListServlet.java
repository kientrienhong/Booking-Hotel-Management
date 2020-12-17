/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.services.ConvertService;
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblfeedback.TblFeedbackDAO;
import trienhk.tblfeedback.TblFeedbackDTO;
import trienhk.tblhotel.TblHotelDAO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDTO;
import trienhk.tblroomtype.TblRoomTypeDAO;
import trienhk.tblroomtype.TblRoomTypeDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "LoadBookedRoomListServlet", urlPatterns = {"/LoadBookedRoomListServlet"})
public class LoadBookedRoomListServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String LOAD_LIST = "loadBookedRoomListMemberPage";

    public void init() {
        logger = Logger.getLogger(LoadBookedRoomListServlet.class.getName());
        BasicConfigurator.configure();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private List<Integer> getListIdHotel(List<TblInvoiceDetailDTO> list) {
        List<Integer> result = new ArrayList<>();

        for (TblInvoiceDetailDTO dto : list) {
            result.add(dto.getIdHotel());
        }

        return result;
    }

    private boolean findIsItExisted(TblInvoiceDetailDTO tblInvoiceDetailDTO, List<TblInvoiceDetailDTO> newList) {
        boolean isExisited = false;

        for (TblInvoiceDetailDTO item : newList) {
            if (tblInvoiceDetailDTO.getIdHotel() == item.getIdHotel() && tblInvoiceDetailDTO.getIdRoomType().equals(item.getIdRoomType())) {
                isExisited = true;
                break;
            }
        }

        return isExisited;
    }

    private List<TblInvoiceDetailDTO> removeDuplicates(List<TblInvoiceDetailDTO> list) {

        List<TblInvoiceDetailDTO> newList = new ArrayList<TblInvoiceDetailDTO>();

        for (TblInvoiceDetailDTO element : list) {
            if (!findIsItExisted(element, newList)) {
                newList.add(element);
            }
        }

        return newList;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(ERROR);
        try {
            // get Booking History
            String txtUserId = request.getParameter("txtUserId");
            ConvertService convertService = new ConvertService();
            TblInvoiceDAO tblInvoiceDAO = new TblInvoiceDAO();
            List<String> listInvoiceId = tblInvoiceDAO.getByRegistrationId(txtUserId);

            TblInvoiceDetailDAO tblInvoiceDetailDAO = new TblInvoiceDetailDAO();
            List<TblInvoiceDetailDTO> listTblInvoiceDetailDTOs = tblInvoiceDetailDAO.findByListIdInvoice(listInvoiceId);
            Collections.reverse(listTblInvoiceDetailDTOs);
            listTblInvoiceDetailDTOs = removeDuplicates(listTblInvoiceDetailDTOs);

            // load list rating
            TblFeedbackDAO tblFeedbackDAO = new TblFeedbackDAO();
            List<TblFeedbackDTO> listFeedbackDTOs = tblFeedbackDAO.getRatingFromInvoiceDetail(listTblInvoiceDetailDTOs, txtUserId);

            // load mapping room type
            TblRoomTypeDAO tblRoomTypeDAO = new TblRoomTypeDAO();
            List<TblRoomTypeDTO> listRoomType = tblRoomTypeDAO.loadAllType();
            Map<String, String> roomTypeMap = convertService.convertListRoomTypeToMap(listRoomType);

            // load mapping id name hotel
            TblHotelDAO tblHotelDAO = new TblHotelDAO();
            List<Integer> listHotelId = getListIdHotel(listTblInvoiceDetailDTOs);
            Map<Integer, String> mapHotel = tblHotelDAO.getNameOfHotel(listHotelId);

            request.setAttribute("ROOM_TYPE_MAP", roomTypeMap);
            request.setAttribute("HOTEL_NAME_MAP", mapHotel);
            request.setAttribute("LIST_INVOICE", listTblInvoiceDetailDTOs);
            request.setAttribute("LIST_RATING", listFeedbackDTOs);

            url = bundle.getString(LOAD_LIST);
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
