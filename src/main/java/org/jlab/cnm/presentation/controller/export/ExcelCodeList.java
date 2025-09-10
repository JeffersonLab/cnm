package org.jlab.cnm.presentation.controller.export;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.jlab.cnm.business.session.ExcelCodeListFacade;
import org.jlab.cnm.business.session.SectorCodeFacade;
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;

/**
 * @author ryans
 */
@WebServlet(
    name = "ExcelCodeList",
    urlPatterns = {"/export/cnd.xlsx"})
public class ExcelCodeList extends HttpServlet {

  @EJB ExcelCodeListFacade excelFacade;

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

    List<SystemCode> systemList = systemFacade.findAll();
    List<TypeCode> typeList = typeFacade.filterList(null, null, null, 0, Integer.MAX_VALUE);
    List<SectorCode> sectorList = sectorFacade.filterList(null, null, 0, Integer.MAX_VALUE);

    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setHeader("content-disposition", "attachment;filename=\"cnd.xlsx\"");

    excelFacade.export(response.getOutputStream(), systemList, typeList, sectorList);
  }
}
