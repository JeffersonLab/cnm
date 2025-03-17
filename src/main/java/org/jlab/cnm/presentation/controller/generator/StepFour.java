package org.jlab.cnm.presentation.controller.generator;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.LocatorCodeFacade;
import org.jlab.cnm.business.session.SectorCodeFacade;
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.cnm.persistence.entity.LocatorCode;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 * @author ryans
 */
@WebServlet(
    name = "StepFour",
    urlPatterns = {"/generator/step-four"})
public class StepFour extends HttpServlet {

  @EJB SystemCodeFacade systemFacade;

  @EJB TypeCodeFacade typeFacade;

  @EJB SectorCodeFacade sectorFacade;

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
    try {
      Character systemCode = ParamConverter.convertCharacter(request, "system");

      SystemCode system = systemFacade.findByCode(systemCode);

      request.setAttribute("system", system);

      String typeCode = request.getParameter("type");

      TypeCode type = typeFacade.findByCode(system.getSCode(), typeCode);

      request.setAttribute("type", type);

      String sectorCode = request.getParameter("sector");

      SectorCode sector = sectorFacade.findByCode(sectorCode);

      request.setAttribute("sector", sector);

      List<LocatorCode> locatorList = locatorFacade.findBySector(sector);

      request.setAttribute("locatorList", locatorList);

      request
          .getRequestDispatcher("/WEB-INF/views/generator/step-four.jsp")
          .forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
