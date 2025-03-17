package org.jlab.cnm.presentation.controller.export;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ryans
 */
@WebServlet(
    name = "PDFCodeList",
    urlPatterns = {"/export/cnd.pdf"})
public class PDFCodeList extends HttpServlet {

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

    request
        .getRequestDispatcher("/convert?filename=cnd.pdf&type=pdf&url=%2Fcnm%2Fexport%2Fcnd.html")
        .forward(request, response);
  }
}
