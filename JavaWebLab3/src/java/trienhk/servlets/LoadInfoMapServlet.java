/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.cart.CartObject;
import trienhk.services.ConvertService;
import trienhk.tblhotel.TblHotelDAO;
import trienhk.tblroomtype.TblRoomTypeDAO;
import trienhk.tblroomtype.TblRoomTypeDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "LoadInfoMapServlet", urlPatterns = {"/LoadInfoMapServlet"})
public class LoadInfoMapServlet extends HttpServlet {

    private Logger logger = null;
    private final String VIEW_CART = "viewCartMember"; 
    private final String VALIDATE_CHECK_OUT = "validateCheckOutMember";
    public void init() {
        logger = Logger.getLogger(LoadInfoMapServlet.class.getName());
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
        String isValidateCheckOutSerlvet = request.getParameter("txtValidateCheckOut");

        String url = bundle.getString(VIEW_CART);

        if (isValidateCheckOutSerlvet != null) {
            url = bundle.getString(VALIDATE_CHECK_OUT);
        }
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {

                CartObject cartObject = (CartObject) session.getAttribute("CART");
                if (cartObject != null) {
                    ConvertService convertService = new ConvertService();
                    TblRoomTypeDAO tblRoomTypeDAO = new TblRoomTypeDAO();
                    List<TblRoomTypeDTO> listRoomType = tblRoomTypeDAO.loadAllType();
                    Map<String, String> mapListRoomType = convertService.convertListRoomTypeToMap(listRoomType);
                    TblHotelDAO tblHotelDAO = new TblHotelDAO();
                    Map<String, String> mapListIdHotel = tblHotelDAO.getNameOfHotelFromCart(cartObject);
                    request.setAttribute("MAP_ROOM_TYPE", mapListRoomType);
                    request.setAttribute("MAP_HOTEL_NAME", mapListIdHotel);
                }
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
