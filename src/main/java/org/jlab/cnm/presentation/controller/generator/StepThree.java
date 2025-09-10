package org.jlab.cnm.presentation.controller.generator;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.jlab.cnm.business.session.SectorCodeFacade;
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 * @author ryans
 */
@WebServlet(
    name = "StepThree",
    urlPatterns = {"/generator/step-three"})
public class StepThree extends HttpServlet {

  @EJB SystemCodeFacade systemFacade;

  @EJB TypeCodeFacade typeFacade;

  @EJB SectorCodeFacade sectorFacade;

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

      SystemCode system = systemFacade.findByCode(systemCode);

      request.setAttribute("system", system);

      String typeCode = request.getParameter("type");

      TypeCode type = typeFacade.findByCode(system.getSCode(), typeCode);

      request.setAttribute("type", type);

      String grouping = request.getParameter("grouping");

      List<SectorCode> sectorList = sectorFacade.filterList(null, grouping, 0, Integer.MAX_VALUE);

      request.setAttribute("sectorList", sectorList);

      List<String> groupingList = sectorFacade.findGroupingList();

      request.setAttribute("groupingList", groupingList);

      request
          .getRequestDispatcher("/WEB-INF/views/generator/step-three.jsp")
          .forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
