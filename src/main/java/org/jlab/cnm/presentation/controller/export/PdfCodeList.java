package org.jlab.cnm.presentation.controller.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ryans
 */
@WebServlet(name = "PdfCodeList", urlPatterns = {"/export/cnd.pdf"})
public class PdfCodeList extends HttpServlet {
    
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

        response.setHeader("content-type", "application/pdf");
        response.setHeader("content-disposition", "attachment; filename=\"cnd.pdf\"");

        URL url  = new URL("https://accweb.acc.jlab.org/puppet-show/pdf?url=https%3A%2F%2Faccweb.acc.jlab.org%2Fcnm%2Fexport%2Fcnd.html&top=0.5in&right=0.5in&bottom=0.5in&left=0.5in");

        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

        InputStream in = con.getInputStream();
        OutputStream out = response.getOutputStream();

        in.transferTo(out);

        in.close();
    }
}
