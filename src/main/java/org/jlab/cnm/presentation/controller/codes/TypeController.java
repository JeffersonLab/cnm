package org.jlab.cnm.presentation.controller.codes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;
import org.jlab.smoothness.presentation.util.ParamConverter;
import org.jlab.smoothness.presentation.util.ParamUtil;

/**
 * @author ryans
 */
@WebServlet(
    name = "TypeController",
    urlPatterns = {"/codes/type"})
public class TypeController extends HttpServlet {

  @EJB TypeCodeFacade typeFacade;

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

    int offset = ParamUtil.convertAndValidateNonNegativeInt(request, "offset", 0);
    int max = ParamUtil.convertAndValidateNonNegativeInt(request, "max", Integer.MAX_VALUE);
    Character sCode = ParamConverter.convertCharacter(request, "system");
    String vvCode = request.getParameter("type");
    String grouping = request.getParameter("grouping");

    List<TypeCode> typeList = typeFacade.filterList(sCode, vvCode, grouping, offset, max);
    long totalRecords = typeFacade.countList(sCode, vvCode, grouping);

    List<String> groupingList = typeFacade.findGroupingList(sCode);

    request.setAttribute("groupingList", groupingList);

    DecimalFormat formatter = new DecimalFormat("###,###");

    String selectionMessage = "All Type Codes ";

    String typeStr = "";

    if (vvCode != null) {
      typeStr = vvCode;
    }

    String systemStr = "";

    if (sCode != null) {
      SystemCode system = systemFacade.findByCode(sCode);

      systemStr = "Unknown System";

      if (system != null) {
        systemStr = system.getDescription();
      }
    }

    if (typeStr != null || sCode != null) {
      selectionMessage = systemStr + " " + typeStr + " Type Codes ";
    }

    if (grouping != null && !grouping.trim().isEmpty()) {
      selectionMessage = selectionMessage + "[" + grouping + "] ";
    }

    selectionMessage = selectionMessage + "{" + formatter.format(totalRecords) + "}";

    request.setAttribute("selectionMessage", selectionMessage);

    request.setAttribute("typeList", typeList);

    List<SystemCode> systemList = systemFacade.findAll();

    request.setAttribute("systemList", systemList);

    request.getRequestDispatcher("/WEB-INF/views/codes/type.jsp").forward(request, response);
  }
}
