/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
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
import trienhk.tblregistration.TblRegistrationDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "ConfirmResetPasswordPageServlet", urlPatterns = {"/ConfirmResetPasswordPageServlet"})
public class ConfirmResetPasswordPageServlet extends HttpServlet {

    private Logger logger = null;
    private final String CONFIRM_RESET_PASS_PAGE = "confirmResetPasswordPageMember";
    private final String SEARCH_PAGE = "searchPage";
    private final String ERROR = "error";

    public void init() {
        logger = Logger.getLogger(ConfirmResetPasswordPageServlet.class.getName());
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
            String oldPassword = request.getParameter("txtOldPassword");
            String txtNewPassword = request.getParameter("txtNewPassword");
            String txtConfirmNewPassword = request.getParameter("txtConfirmNewPassword");
            String txtUserId = request.getParameter("txtRegistrationId");
            byte[] result = Base64.getDecoder().decode(txtUserId);
            String registrationId = new String(result);

            boolean isValid = true;

            if (txtNewPassword.trim().length() < 4 || txtNewPassword.trim().length() > 20) {
                isValid = false;
                request.setAttribute("ERROR_NEW_PASSWORD", "New password must be in range 4 - 20");
            } else {
                if (!txtConfirmNewPassword.equals(txtNewPassword)) {
                    isValid = false;
                    request.setAttribute("ERROR_CONFIRM_PASSWORD", "Confirm new password must be match with new password");
                }
            }
            if (isValid) {
                TblRegistrationDAO tblRegistrationDAO = new TblRegistrationDAO();
                boolean resetPassword = tblRegistrationDAO.resetPassword(txtNewPassword, registrationId, oldPassword);
                if (resetPassword) {
                    url = bundle.getString(SEARCH_PAGE);
                } else {
                    request.setAttribute("ERROR_UPDATE", "INVALID PASSWORD!!");
                    url = bundle.getString(CONFIRM_RESET_PASS_PAGE);
                }
            }
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
        } finally {
            if (url.equals("search.jsp")) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Change password sucessfully');");
                out.println("location='searchPage';");
                out.println("</script>");
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
