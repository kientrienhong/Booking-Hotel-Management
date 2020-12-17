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
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblfeedback.TblFeedbackDAO;
import trienhk.tblfeedback.TblFeedbackDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "FeedbackServlet", urlPatterns = {"/FeedbackServlet"})
public class FeedbackServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String LOAD_LIST = "loadBookedRoomListMember";

    public void init() {
        logger = Logger.getLogger(FeedbackServlet.class.getName());
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

        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(ERROR);
        try {
            String txtUserId = request.getParameter("txtUserId");
            String txtHotelId = request.getParameter("txtHotelId");
            String txtRoomTypeId = request.getParameter("txtRoomTypeId");
            String cbRating = request.getParameter("cbRating");
            String txtInvoiceId = request.getParameter("txtInvoiceId");
            int intHotelId = Integer.parseInt(txtHotelId);
            int intCBRating = Integer.parseInt(cbRating);
            TblInvoiceDAO tblInvoiceDAO = new TblInvoiceDAO();
            Date currentDate = new Date(System.currentTimeMillis());
            Date checkOutDate = tblInvoiceDAO.findDateCheckOutById(txtInvoiceId);
            if (checkOutDate.after(currentDate)) {
                request.setAttribute("ERROR_FEEDBACK", "You have to check out before giving feedback");
            } else {
                TblFeedbackDAO tblFeedbackDAO = new TblFeedbackDAO();
                TblFeedbackDTO tblFeedbackDTO = tblFeedbackDAO.getTblFeedbackDTO(txtUserId, intHotelId, txtRoomTypeId);

                if (tblFeedbackDTO != null) {
                    tblFeedbackDTO.setRating(intCBRating);
                    tblFeedbackDAO.updateRating(tblFeedbackDTO);
                } else {
                    tblFeedbackDTO = new TblFeedbackDTO(txtUserId, intHotelId, txtRoomTypeId, intCBRating, "Active");
                    tblFeedbackDAO.insertFeedback(tblFeedbackDTO);
                }
            }
            
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
