package org.jlab.cnm.presentation.controller.codes;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import org.jlab.cnm.business.session.SectorCodeFacade;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.smoothness.presentation.util.ParamUtil;

/**
 * @author ryans
 */
@WebServlet(
    name = "SectorController",
    urlPatterns = {"/codes/sector"})
public class SectorController extends HttpServlet {

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

    int offset = ParamUtil.convertAndValidateNonNegativeInt(request, "offset", 0);
    int max = ParamUtil.convertAndValidateNonNegativeInt(request, "max", Integer.MAX_VALUE);
    String xxCode = request.getParameter("sector");
    String grouping = request.getParameter("grouping");

    List<SectorCode> sectorList = sectorFacade.filterList(xxCode, grouping, offset, max);
    long totalRecords = sectorFacade.countList(xxCode, grouping);

    request.setAttribute("sectorList", sectorList);

    List<String> groupingList = sectorFacade.findGroupingList();

    request.setAttribute("groupingList", groupingList);

    DecimalFormat formatter = new DecimalFormat("###,###");

    String selectionMessage = "All Sector Codes ";

    if (xxCode != null && !xxCode.trim().isEmpty()) {
      selectionMessage = xxCode + " Sector Codes ";
    }

    if (grouping != null && !grouping.trim().isEmpty()) {
      selectionMessage = selectionMessage + "[" + grouping + "] ";
    }

    selectionMessage = selectionMessage + "{" + formatter.format(totalRecords) + "}";

    request.setAttribute("selectionMessage", selectionMessage);

    request.getRequestDispatcher("/WEB-INF/views/codes/sector.jsp").forward(request, response);
  }
}
