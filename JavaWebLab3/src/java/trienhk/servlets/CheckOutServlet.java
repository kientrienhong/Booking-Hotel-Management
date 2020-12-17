/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.cart.CartObject;
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblInvoice.TblInvoiceDTO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDTO;
import trienhk.tblroom.TblRoomDTO;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private Logger logger = null;
    private final String SEND_CONFIRM_BOOK = "sendConfirmBookingMember";
    private final String ERROR = "error";

    public void init() {
        logger = Logger.getLogger(CheckOutServlet.class.getName());
        BasicConfigurator.configure();
    }

    private String random() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString.toUpperCase();
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
        Connection conn = null;
        String url = bundle.getString(ERROR);
        
        try {
            String txtRecipientName = request.getParameter("txtRecipientName");

            String txtCheckIn = request.getParameter("txtCheckIn");
            String txtCheckOut = request.getParameter("txtCheckOut");
            String phone = request.getParameter("txtPhone");
            String txtUserId = request.getParameter("txtUserId");

            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cartObject = (CartObject) session.getAttribute("CART");
                if (cartObject != null) {

                    // prepare tblInvoiceDTO
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date checkInDate = new Date(sdf.parse(txtCheckIn).getTime());
                    Date checkOutDate = new Date(sdf.parse(txtCheckOut).getTime());
                    double totalPrice = cartObject.totalPrice();
                    Date cuurentDate = new Date(System.currentTimeMillis());
                    String[] splitedDate = cuurentDate.toString().split("-");
                    String idInvoice = txtUserId + "_" + random() + "_" + splitedDate[0] + splitedDate[1] + splitedDate[2];
                    Long differenceDate = (Long) session.getAttribute("DIFFERENCE_DATE");
                    TblInvoiceDTO tblInvoiceDTO = new TblInvoiceDTO(idInvoice, txtUserId, checkInDate, checkOutDate, differenceDate * totalPrice, cuurentDate, "Active", phone, txtRecipientName, false);

                    // prepare list tblInvoiceDetails
                    List<TblInvoiceDetailDTO> listTblInvoiceDetail = new ArrayList<TblInvoiceDetailDTO>();
                    for (String key : cartObject.getItems().keySet()) {
                        TblRoomDTO roomDTO = cartObject.getItems().get(key);
                        int idHotel = roomDTO.getIdHotel();
                        int quantity = roomDTO.getTotalAmount();
                        double price = roomDTO.getPrice();
                        String roomTypeId = roomDTO.getIdRoomType();
                        TblInvoiceDetailDTO tblInvoiceDetailDTO = new TblInvoiceDetailDTO(idInvoice, roomTypeId, quantity, idHotel, price, "Active");
                        listTblInvoiceDetail.add(tblInvoiceDetailDTO);
                    }

                    // insert transaction
                    conn = DBHelpers.getConnection();
                    TblInvoiceDAO tblInvoiceDAO = new TblInvoiceDAO();
                    TblInvoiceDetailDAO tblInvoiceDetailDAO = new TblInvoiceDetailDAO();
                    boolean checkInserInvoice = tblInvoiceDAO.insert(tblInvoiceDTO, conn);
                    boolean checkInsertInvoiceDetail = tblInvoiceDetailDAO.insert(listTblInvoiceDetail, conn);

                    if (checkInserInvoice && checkInsertInvoiceDetail) {
                        conn.commit();
                        request.setAttribute("INVOICE_CODE", idInvoice);
                        session.removeAttribute("CART");
                        session.removeAttribute("PREVIOUS_SEARCH");
                        url = bundle.getString(SEND_CONFIRM_BOOK);
                    } 
                }
            }
        } catch (NamingException ex) {
            logger.error(ex);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } catch (ParseException ex) {
            logger.error(ex);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
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
