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
@WebServlet(name = "ModifyQuantityServlet", urlPatterns = {"/ModifyQuantityServlet"})
public class ModifyQuantityServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String LOAD_INFO = "loadInfoMapMember";

    public void init() {
        logger = Logger.getLogger(ModifyQuantityServlet.class.getName());
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
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = resourceBundle.getString(ERROR);
        try {
            String txtTime = request.getParameter("txtTime");
            String[] splitedTxtTime = txtTime.split("_");
            String txtCheckIn = splitedTxtTime[0];
            String txtCheckOut = splitedTxtTime[1];
            String txtId = request.getParameter("txtId");
            String txtIsIncrease = request.getParameter("txtIsIncrease");
            boolean isIncrease = txtIsIncrease.equals("yes");
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cartObject = (CartObject) session.getAttribute("CART");
                if (cartObject != null) {
                    TblRoomDTO dtoCurrentInCart = cartObject.getItems().get(txtId);
                    String[] splitedTxtId = txtId.split("_");
                    TblRoomDAO tblRoomDAO = new TblRoomDAO();
                    TblRoomDTO dto = tblRoomDAO.getRoom(Integer.parseInt(splitedTxtId[0]), splitedTxtId[1]);
                    TblInvoiceDAO invoiceDAO = new TblInvoiceDAO();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateCheckIn = new Date(sdf.parse(txtCheckIn).getTime());
                    Date dateCheckOut = new Date(sdf.parse(txtCheckOut).getTime());
                    List<String> listInvoice = invoiceDAO.getListAlreadyBookInvoiceBasedOnDate(dateCheckIn, dateCheckOut);
                    TblInvoiceDetailDAO tblInvoiceDetailDAO = new TblInvoiceDetailDAO();
                    dto = tblInvoiceDetailDAO.getQuantityAvailableRoom(listInvoice, dto);

                    if (isIncrease) {
                        if (dtoCurrentInCart.getTotalAmount() >= dto.getTotalAmount()) {
                            request.setAttribute("ERROR_OUT_STOCK", "Out of stock");
                        } else {
                            cartObject.modifyQuantityOfRoom(txtId, isIncrease);
                        }
                    } else {
                        cartObject.modifyQuantityOfRoom(txtId, isIncrease);
                        if (cartObject.getItems() == null) {
                            session.removeAttribute("PREVIOUS_SEARCH");
                            session.removeAttribute("DIFFERENCE_DATE");
                        }
                    }

                    session.setAttribute("CART", cartObject);
                    url = resourceBundle.getString(LOAD_INFO);
                }
            }
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (NumberFormatException ex) {
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
