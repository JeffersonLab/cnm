package org.jlab.cnm.presentation.controller.codes;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.LocatorCodeFacade;
import org.jlab.cnm.persistence.entity.LocatorCode;

/**
 * @author ryans
 */
@WebServlet(
    name = "LocatorController",
    urlPatterns = {"/codes/locator"})
public class LocatorController extends HttpServlet {

  @EJB LocatorCodeFacade locatorFacade;

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
    List<LocatorCode> locatorList = locatorFacade.findAll();

    request.setAttribute("locatorList", locatorList);

    request.getRequestDispatcher("/WEB-INF/views/codes/locator.jsp").forward(request, response);
  }
}
