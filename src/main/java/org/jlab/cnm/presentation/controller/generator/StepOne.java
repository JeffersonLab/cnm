package org.jlab.cnm.presentation.controller.generator;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.cnm.persistence.entity.SystemCode;

/**
 * @author ryans
 */
@WebServlet(
    name = "StepOne",
    urlPatterns = {"/generator/step-one"})
public class StepOne extends HttpServlet {

  @EJB SystemCodeFacade systemFacade;

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

    List<SystemCode> systemList = systemFacade.findAll();

    request.setAttribute("systemList", systemList);

    request
        .getRequestDispatcher("/WEB-INF/views/generator/step-one.jsp")
        .forward(request, response);
  }
}
