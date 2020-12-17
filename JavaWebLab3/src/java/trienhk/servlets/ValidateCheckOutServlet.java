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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import trienhk.checkout.CheckOutError;
import trienhk.tblInvoice.TblInvoiceDAO;
import trienhk.tblinvoicedetail.TblInvoiceDetailDAO;
import trienhk.tblroom.TblRoomDAO;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "ValidateCheckOutServlet", urlPatterns = {"/ValidateCheckOutServlet"})
public class ValidateCheckOutServlet extends HttpServlet {

    private Logger logger = null;
    private final String VIEW_CART = "viewCartMember";
    private final String CHECK_OUT_SERVLET = "checkOutMember";
    private final String ERROR = "error";

    public void init() {
        logger = Logger.getLogger(ValidateCheckOutServlet.class.getName());
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
            String txtRecipientName = request.getParameter("txtRecipientName");
            String txtCheckIn = request.getParameter("txtCheckIn");
            String txtCheckOut = request.getParameter("txtCheckOut");
            String phone = request.getParameter("txtPhone");
            CheckOutError checkOutError = new CheckOutError();
            Date checkInDate = null;
            Date checkOutDate = null;
            Map<String, String> mapRoomTypeId = (Map<String, String>) request.getAttribute("MAP_ROOM_TYPE");
            Map<String, String> hotelName = (Map<String, String>) request.getAttribute("MAP_HOTEL_NAME");
            boolean isValid = true;

            if (txtRecipientName.trim().length() == 0) {
                isValid = false;
                checkOutError.setNameError("Name can not be empty");
            }

            if (txtCheckIn.trim().length() == 0) {
                isValid = false;
                checkOutError.setCheckInDateError("Check In can not be empty");
            }

            if (txtCheckOut.trim().length() == 0) {
                isValid = false;
                checkOutError.setCheckOutDateError("Check in can not be emtpty");
            }

            if (txtCheckIn.trim().length() > 0 && txtCheckOut.trim().length() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    checkInDate = new Date(sdf.parse(txtCheckIn).getTime());
                    checkOutDate = new Date(sdf.parse(txtCheckOut).getTime());
                    Date current = new Date(System.currentTimeMillis());

                    if (checkInDate.before(current)) {
                        isValid = false;
                        checkOutError.setCheckInDateError("Check in date must be after current date");
                    }

                    if (checkInDate.after(checkOutDate)) {
                        isValid = false;
                        checkOutError.setDateError("Check in date must after check out date");
                    }

                    if (checkInDate.getTime() == checkOutDate.getTime()) {
                        isValid = false;
                        checkOutError.setDateError("2 date can not be the same");
                    }

                } catch (ParseException e) {
                    isValid = false;
                    checkOutError.setDateError("Wrong format!");
                    logger.error(e);
                }
            }

            if (phone.trim().length() == 0) {
                isValid = false;
                checkOutError.setPhoneError("Phone can not be empty!");
            } else {
                Pattern pattern = Pattern.compile("[0-9]{10}");
                Matcher matcher = pattern.matcher(phone);
                if (!matcher.find()) {
                    isValid = false;
                    checkOutError.setPhoneError("Wrong format must input 10 number!");
                }
            }
            if (isValid) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        TblRoomDAO tblRoomDAO = new TblRoomDAO();
                        TblInvoiceDAO invoiceDAO = new TblInvoiceDAO();
                        TblInvoiceDetailDAO tblInvoiceDetailDAO = new TblInvoiceDetailDAO();
                        List<String> listInvoice = invoiceDAO.getListAlreadyBookInvoiceBasedOnDate(checkInDate, checkOutDate);
                        String error = "";
                        for (String key : cart.getItems().keySet()) {
                            TblRoomDTO currentRoomInCart = cart.getItems().get(key);
                            int idHotel = currentRoomInCart.getIdHotel();
                            int currentAmountInCart = currentRoomInCart.getTotalAmount();
                            String roomTypeId = currentRoomInCart.getIdRoomType();
                            TblRoomDTO roomInDB = tblRoomDAO.getRoom(idHotel, roomTypeId);
                            roomInDB = tblInvoiceDetailDAO.getQuantityAvailableRoom(listInvoice, roomInDB);
                            if (currentAmountInCart > roomInDB.getTotalAmount()) {
                                error += "Total of " + mapRoomTypeId.get(roomTypeId)
                                        + " of " + hotelName.get(Integer.toString(idHotel)) + "hotel is remaining: " + roomInDB.getTotalAmount() + "<br/>";
                            }
                        }
                        if (error.length() > 0) {
                            checkOutError.setOutOfStockError(error);
                            request.setAttribute("ERROR_CHECK_OUT", checkOutError);
                        } else {
                            url = bundle.getString(CHECK_OUT_SERVLET);
                        }

                        // check if user changes the check in check out date
                        String newBookedDate = txtCheckIn + "_" + txtCheckOut;
                        String previousDate = (String) session.getAttribute("PREVIOUS_SEARCH");
                        // yes return view cart for user to confirm
                        if (!newBookedDate.equals(previousDate)) {
                            session.setAttribute("PREVIOUS_SEARCH", newBookedDate);
                            long differenceTime = checkOutDate.getTime() - checkInDate.getTime();
                            long differenceDays = (differenceTime / (1000 * 60 * 60 * 24)) % 365;
                            session.setAttribute("DIFFERENCE_DATE", differenceDays);
                            request.setAttribute("CHANGE_DATE", "You have changed date you must press check out again to confirm");
                            url = bundle.getString(VIEW_CART);
                        }
                    }
                }
            } else {
                request.setAttribute("ERROR_CHECK_OUT", checkOutError);
                url = bundle.getString(VIEW_CART);
            }
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
