/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
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
import trienhk.tblregistration.TblRegistrationDAO;
import trienhk.tblregistration.TblRegistrationDTO;
import trienhk.tblregistration.TblRegistrationErrorSignUp;
import trienhk.tblrole.TblRoleDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    private final String SIGN_UP_JSP = "createAccountJsp";
    private final String SEARCH = "";
    private final String ERROR = "error";
    private Logger logger = null;

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
        logger = Logger.getLogger(SignUpServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = ERROR;
        boolean valid = true;
        TblRegistrationErrorSignUp error = new TblRegistrationErrorSignUp();

        try {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirmPassword");
            String name = request.getParameter("txtNameRegistraion");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhone");

            if (email.trim().length() == 0) {
                valid = false;
                error.setEmailError("Email can not be empty!");
            } else {
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    error.setEmailError("Wrong format email");
                }
            }

            if (password.trim().length() < 4 || password.trim().length() > 20) {
                valid = false;
                error.setPasswordError("Password length must be in range 4 - 20");
            } else {
                if (!confirm.equals(password)) {
                    valid = false;
                    error.setPasswordConfirmError("Confirm must be match with Password!");
                }
            }

            if (name.trim().length() == 0) {
                valid = false;
                error.setNameError("Name can not be empty!");
            }
            if (address.trim().length() == 0) {
                valid = false;
                error.setAddressError("Address can not be empty!");
            }
            if (phone.trim().length() == 0) {
                valid = false;
                error.setPhoneError("Phone can not be empty!");
            } else {
                Pattern pattern = Pattern.compile("[0-9]{10}");
                Matcher matcher = pattern.matcher(phone);
                if (!matcher.find()) {
                    valid = false;
                    error.setPhoneError("Wrong format must input 10 number!");
                }
            }

            if (valid) {
                TblRegistrationDAO dao = new TblRegistrationDAO();
                Date createdDate = new Date(System.currentTimeMillis());
                TblRegistrationDTO dto = new TblRegistrationDTO(email, password, name, "MEM", "Active", address, phone, createdDate);
                boolean result = dao.signUp(dto);
                if (result) {
                    url = SEARCH;
                    HttpSession session = request.getSession();
                    dto.setPassword("****");
                    session.setAttribute("DTO", dto);
                    TblRoleDAO roleDAO = new TblRoleDAO();
                    String nameRole = roleDAO.getNameById(dto.getRoleId());
                    request.setAttribute("NAME_ROLE", nameRole);
                }
            } else {
                request.setAttribute("ERROR", error);
                url = SIGN_UP_JSP;
            }
        } catch (NamingException e) {
            logger.error(e);
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate")) {
                valid = false;
                error.setEmailExisted("Email has been existed!");
                request.setAttribute("ERROR", error);
            } else {
                logger.error(ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(bundle.getString(url));
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
