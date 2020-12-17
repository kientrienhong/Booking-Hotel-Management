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
import trienhk.services.ConvertService;
import trienhk.tblhotel.TblHotelDAO;
import trienhk.tblhotel.TblHotelDTO;
import trienhk.tblhotel.TblHotelError;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String GET_AVAILABLE = "getAvailableRoomsServlet";
    private final String SEARCH_PAGE = "searchPage";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() {
        logger = Logger.getLogger(SearchServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("txtName");
        String area = request.getParameter("txtArea");
        String txtCheckIn = request.getParameter("txtCheckIn");
        String txtCheckOut = request.getParameter("txtCheckOut");
        String txtAmount = request.getParameter("txtAmount");
        String roleName = (String) request.getAttribute("NAME_ROLE");

        TblHotelError error = new TblHotelError();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date dateCheckIn = null;
        Date dateCheckOut = null;
        boolean isValid = true;
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(ERROR);

        try {
            if (name.trim().length() == 0) {
                if (area.trim().length() == 0) {
                    error.setEmptyError("You must input name or area! ");
                    isValid = false;
                }
            }

            if (txtCheckIn.trim().length() == 0) {
                isValid = false;
                error.setCheckInError("Check in can not be empty!");
            } else {
                try {
                    dateCheckIn = new Date(sdf.parse(txtCheckIn).getTime());
                    Date current = new Date(System.currentTimeMillis());
                    if (dateCheckIn.before(current)) {
                        isValid = false;
                        error.setCheckInError("The check in date must be after current date!");
                    }

                } catch (ParseException ex) {
                    logger.error(ex);
                    isValid = false;
                    error.setCheckInError("Wrong format!");
                }
            }

            if (txtCheckOut.trim().length() == 0) {
                isValid = false;
                error.setCheckOutError("Check out can not be empty!");
            } else {
                try {
                    dateCheckOut = new Date(sdf.parse(txtCheckOut).getTime());
                } catch (ParseException ex) {
                    logger.error(ex);
                    isValid = false;
                    error.setCheckOutError("Wrong format!");
                }
            }

            if (dateCheckIn != null && dateCheckOut != null) {
                if (dateCheckIn.after(dateCheckOut)) {
                    isValid = false;
                    error.setInvalidDateError("Check in date must be before Check out date");
                } else if (dateCheckIn.getTime() == dateCheckOut.getTime()) {
                    isValid = false;
                    error.setInvalidDateError("2 date can not be the same");
                }
            }

            if (txtAmount.length() == 0) {
                isValid = false;
                error.setAmountError("Amount can not be empty!");
            } else {
                try {
                    int amount = Integer.parseInt(txtAmount);

                    if (amount <= 0 && !roleName.equals("admin")) {
                        isValid = false;
                        error.setAmountError("Amount must be larger 0");
                    } else if (amount < 0 && roleName.equals("admin")) {
                        isValid = false;
                        error.setAmountError("Amount must be equal or larger 0");
                    }

                } catch (Exception e) {
                    logger.error(e);
                    isValid = false;
                    error.setAmountError("Amount must be numeric!");
                }
            }

            if (isValid) {
                TblHotelDAO tblHotelDAO = new TblHotelDAO();
                List<TblHotelDTO> listHotel = null;
                ConvertService searchService = new ConvertService();
                if (name.trim().length() > 0 && area.trim().length() > 0) {
                    if (!roleName.equals("admin")) {
                        listHotel = tblHotelDAO.findByLikeAreaAndName(area, name);
                    } else {
                        listHotel = tblHotelDAO.findByLikeAreaAndNameAdmin(area, name);
                    }
                } else {
                    if (name.trim().length() > 0) {
                        if (!roleName.equals("admin")) {
                            listHotel = tblHotelDAO.findByLikeName(name);
                        } else {
                            listHotel = tblHotelDAO.findByLikeNameAdmin(name);
                        }
                    } else {
                        if (!roleName.equals("admin")) {
                            listHotel = tblHotelDAO.findByLikeArea(area);
                        } else {
                            listHotel = tblHotelDAO.findByLikeAreaAdmin(area);
                        }
                    }
                }
                Map<TblHotelDTO, List<TblRoomDTO>> map = searchService.convertListToMap(listHotel);
                request.setAttribute("MAP_SEARCH", map);
                url = bundle.getString(GET_AVAILABLE);
            } else {
                url = bundle.getString(SEARCH_PAGE);
                request.setAttribute("ERROR_SEARCH", error);
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
