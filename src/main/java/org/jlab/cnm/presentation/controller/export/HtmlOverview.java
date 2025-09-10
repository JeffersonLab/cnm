package org.jlab.cnm.presentation.controller.export;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ryans
 */
@WebServlet(
    name = "HtmlOverview",
    urlPatterns = {"/export/overview.html"})
public class HtmlOverview extends HttpServlet {

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

    if (request.getParameter("attachment") != null) {
      response.setContentType("text/html;charset=UTF-8");
      response.setHeader("content-disposition", "attachment;filename=\"overview.html\"");
    }

    request.getRequestDispatcher("/WEB-INF/views/overview-export.jsp").forward(request, response);
  }
}
