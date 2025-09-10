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
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 * @author ryans
 */
@WebServlet(
    name = "StepTwo",
    urlPatterns = {"/generator/step-two"})
public class StepTwo extends HttpServlet {

  @EJB SystemCodeFacade systemFacade;

  @EJB TypeCodeFacade typeFacade;

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

    try {
      Character systemCode = ParamConverter.convertCharacter(request, "system");
      String grouping = request.getParameter("grouping");

      SystemCode system = systemFacade.findByCode(systemCode);

      request.setAttribute("system", system);

      List<TypeCode> typeList =
          typeFacade.filterList(systemCode, null, grouping, 0, Integer.MAX_VALUE);

      request.setAttribute("typeList", typeList);

      List<String> groupingList = typeFacade.findGroupingList(systemCode);

      request.setAttribute("groupingList", groupingList);

      request
          .getRequestDispatcher("/WEB-INF/views/generator/step-two.jsp")
          .forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
