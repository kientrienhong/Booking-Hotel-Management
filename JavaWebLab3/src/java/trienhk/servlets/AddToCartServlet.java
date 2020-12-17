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
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;
import trienhk.tblroom.TblRoomDAO;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String SEARCH = "search";

    public void init() {
        logger = Logger.getLogger(AddToCartServlet.class.getName());
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
        String txtHotelId = request.getParameter("txtHotelId");
        String txtIdRoomType = request.getParameter("txtIdRoomType");
        String idItemInCart = txtHotelId + "_" + txtIdRoomType;
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(ERROR);
        String txtCheckIn = request.getParameter("txtCheckIn");
        String txtCheckOut = request.getParameter("txtCheckOut");
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                String previousCheckInCheckOutSearch = (String) sesion.getAttribute("PREVIOUS_SEARCH");

                if (previousCheckInCheckOutSearch != null) {
                    if (!previousCheckInCheckOutSearch.equals(txtCheckIn + "_" + txtCheckOut)) {
                        request.setAttribute("ERROR_ADD_CART", "We support only one check in check out room add! You must delete all item to reset searched dates");
                        return;
                    }
                }

                CartObject cartObject = (CartObject) sesion.getAttribute("CART");
                if (cartObject == null) {
                    cartObject = new CartObject();
                }

                int currentItemQuantityInCart = cartObject.getQuantityOfRoom(idItemInCart);

                // check avaailable amount in db
                TblRoomDAO tblRoomDAO = new TblRoomDAO();
                TblRoomDTO dto = tblRoomDAO.getRoom(Integer.parseInt(txtHotelId), txtIdRoomType);
                TblInvoiceDAO invoiceDAO = new TblInvoiceDAO();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateCheckIn = new Date(sdf.parse(txtCheckIn).getTime());
                Date dateCheckOut = new Date(sdf.parse(txtCheckOut).getTime());
                List<String> listInvoice = invoiceDAO.getListAlreadyBookInvoiceBasedOnDate(dateCheckIn, dateCheckOut);
                TblInvoiceDetailDAO tblInvoiceDetailDAO = new TblInvoiceDetailDAO();
                dto = tblInvoiceDetailDAO.getQuantityAvailableRoom(listInvoice, dto);

                if (currentItemQuantityInCart >= dto.getTotalAmount()) {
                    request.setAttribute("ERROR_ADD_CART", "Out of stock");
                } else {
                    dto.setTotalAmount(1);
                    cartObject.add(dto);
                    sesion.setAttribute("PREVIOUS_SEARCH", txtCheckIn + "_" + txtCheckOut);
                    long differenceTime = dateCheckOut.getTime() - dateCheckIn.getTime();
                    long differenceDays = (differenceTime / (1000 * 60 * 60 * 24)) % 365;
                    sesion.setAttribute("DIFFERENCE_DATE", differenceDays);
                }

                request.setAttribute("CART_SIZE", cartObject.getTotalItemsAmount());
                sesion.setAttribute("CART", cartObject);
                url = bundle.getString(SEARCH);
            }

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
