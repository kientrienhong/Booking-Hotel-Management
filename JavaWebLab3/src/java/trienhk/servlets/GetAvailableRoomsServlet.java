/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import trienhk.tblroomtype.TblRoomTypeDAO;
import trienhk.tblroomtype.TblRoomTypeDTO;
import trienhk.services.ConvertService;
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblhotel.TblHotelDTO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "GetAvailableRoomsServlet", urlPatterns = {"/GetAvailableRoomsServlet"})
public class GetAvailableRoomsServlet extends HttpServlet {

    private Logger logger = null;
    private final String SEARCH_PAGE = "searchPage";
    private final String ERROR = "error";
    public void init() {
        logger = Logger.getLogger(GetAvailableRoomsServlet.class.getName());
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<TblHotelDTO, List<TblRoomDTO>> map
                = (Map<TblHotelDTO, List<TblRoomDTO>>) request.getAttribute("MAP_SEARCH");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(ERROR);

        try {
            Map<TblHotelDTO, List<TblRoomDTO>> result = null;
            TblInvoiceDetailDAO invoiceDetailDAO = new TblInvoiceDetailDAO();
            TblInvoiceDAO invoiceDAO = new TblInvoiceDAO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String txtCheckIn = request.getParameter("txtCheckIn");
            String txtCheckOut = request.getParameter("txtCheckOut");
            String txtAmount = request.getParameter("txtAmount");
            Date dateCheckIn = new Date(sdf.parse(txtCheckIn).getTime());
            Date dateCheckOut = new Date(sdf.parse(txtCheckOut).getTime());
            int amount = Integer.parseInt(txtAmount);
            List<String> listTblInvoiceId = invoiceDAO.getListAlreadyBookInvoiceBasedOnDate(dateCheckIn, dateCheckOut);
            result = invoiceDetailDAO.getSuitableHotel(listTblInvoiceId, map, amount);

            TblRoomTypeDAO tblRoomTypeDAO = new TblRoomTypeDAO();
            List<TblRoomTypeDTO> listRoomTypeDTO = tblRoomTypeDAO.loadAllType();
            ConvertService searchService = new ConvertService();
            Map<String, String> roomTypeMapping = searchService.convertListRoomTypeToMap(listRoomTypeDTO);

            request.setAttribute("SEARCH_RESULTS", result);
            request.setAttribute("ROOM_TYPE_MAP", roomTypeMapping);
            url = bundle.getString(SEARCH_PAGE);
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ParseException ex) {
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
