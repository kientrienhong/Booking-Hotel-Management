/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.tblInvoice.TblInvoiceDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "ConfirmBookingServlet", urlPatterns = {"/ConfirmBookingServlet"})
public class ConfirmBookingServlet extends HttpServlet {

    private final String ERROR = "error";
    private Logger logger = null;

    public void init() {
        logger = Logger.getLogger(ConfirmBookingServlet.class.getName());
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
        String txtInvoiceId = request.getParameter("txtInvoiceId");

        if (txtInvoiceId != null) {
            byte[] byteInvoiceId = Base64.getDecoder().decode(txtInvoiceId);
            String invoiceId = new String(byteInvoiceId);
            String url = ERROR;
            boolean result = false;
            try {
                TblInvoiceDAO tblInvoiceDAO = new TblInvoiceDAO();
                result = tblInvoiceDAO.confirmBooking(invoiceId);
            } catch (NamingException ex) {
                logger.error(ex);
            } catch (SQLException ex) {
                logger.error(ex);
            } finally {
                if (result) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Confirm sucessfully!');");
                    out.println("location='searchPage';");
                    out.println("</script>");
                } else {
                    response.sendRedirect(url);
                }
                out.close();
            }
        } else {
            response.sendRedirect(ERROR);
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
