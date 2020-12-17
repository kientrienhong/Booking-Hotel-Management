/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SendConfirmBookingServlet", urlPatterns = {"/SendConfirmBookingServlet"})
public class SendConfirmBookingServlet extends HttpServlet {

    private Logger logger = null;
    private final String ERROR = "error";
    private final String SEARCH_PAGE = "searchPage";

    public void init() {
        logger = Logger.getLogger(SendConfirmBookingServlet.class.getName());
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
        String sender = "trienhkse140737@fpt.edu.vn";
        String url = ERROR;

        try {
            Properties properties = System.getProperties();
            //fill all the information like host name etc.  
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "25");
            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(sender, "Kientrien20");

                }

            });
            MimeMessage message = new MimeMessage(session);

            String invoiceId = (String) request.getAttribute("INVOICE_CODE");
            String txtUserId = request.getParameter("txtUserId");
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(txtUserId));
            message.setSubject("Confirm booking");
            String encryptedInvoiceId = Base64.getEncoder().encodeToString(invoiceId.getBytes());
            String txtInvoiceId = "?txtInvoiceId=" + encryptedInvoiceId;
            String token = "http://localhost:8084/JavaWebLab3/confirmBooking" + txtInvoiceId;
            message.setText(token);
            Transport.send(message);
            url = SEARCH_PAGE;
        } catch (MessagingException ex) {
            logger.error(ex);
        } finally {
            if (url.equals(SEARCH_PAGE)) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('We have sent confirm mail. Please check your mail!');");
                out.println("location='" + url + "';");
                out.println("</script>");
            } else {
                response.sendRedirect(ERROR);
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
