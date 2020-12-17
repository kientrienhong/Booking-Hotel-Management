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
import java.util.ArrayList;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblInvoice.TblInvoiceDTO;
import trienhk.tblInvoice.TblInvoiceError;
import trienhk.tblhotel.TblHotelDAO;
import trienhk.tblhotel.TblHotelDTO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SearchHistoryBookingServlet", urlPatterns = {"/SearchHistoryBookingServlet"})
public class SearchHistoryBookingServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String BOOKING_HISTORY = "bookingHistoryPageMember";

    public void init() {
        logger = Logger.getLogger(SearchHistoryBookingServlet.class.getName());
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
    private List<String> removeDuplicates(List<String> list) {

        List<String> newList = new ArrayList<String>();

        for (String element : list) {

            if (!newList.contains(element)) {

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
            String name = request.getParameter("txtName");
            String txtBookingDate = request.getParameter("txtBookingDate");
            String txtUserId = request.getParameter("txtUserId");
            boolean isValid = true;
            TblInvoiceError error = new TblInvoiceError();
            if (name.length() == 0 && txtBookingDate.length() == 0) {
                isValid = false;
                error.setSearchError("You must input name or booking date");
            }
            if (isValid) {
                TblInvoiceDAO tblInvoiceDAO = new TblInvoiceDAO();
                TblHotelDAO tblHotelDAO = new TblHotelDAO();
                List<TblInvoiceDTO> listTblInvoiceDTOs = null;
                TblInvoiceDetailDAO invoiceDetailDAO = new TblInvoiceDetailDAO();
                if (name.trim().length() > 0 && txtBookingDate.length() > 0) {
                    List<TblHotelDTO> list = tblHotelDAO.findByLikeName(name);
                    List<String> listInvoiceId = invoiceDetailDAO.getByHotelId(list);
                    listInvoiceId = removeDuplicates(listInvoiceId);
                    Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtBookingDate).getTime());
                    listTblInvoiceDTOs = tblInvoiceDAO.getByHotelIdAndBookingDate(listInvoiceId, txtUserId, date);
                } else {
                    if (name.trim().length() > 0) {
                        List<TblHotelDTO> list = tblHotelDAO.findByLikeName(name);
                        List<String> listInvoiceId = invoiceDetailDAO.getByHotelId(list);
                        listInvoiceId = removeDuplicates(listInvoiceId);
                        listTblInvoiceDTOs = tblInvoiceDAO.getByHotelId(listInvoiceId, txtUserId);
                    } else {
                        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtBookingDate).getTime());
                        listTblInvoiceDTOs = tblInvoiceDAO.getByDateBooking(date, txtUserId);
                    }
                }

                request.setAttribute("LIST_BOOKING", listTblInvoiceDTOs);
                url = bundle.getString(BOOKING_HISTORY);
            } else {
                request.setAttribute("ERROR", error);
                url = bundle.getString(BOOKING_HISTORY);
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
