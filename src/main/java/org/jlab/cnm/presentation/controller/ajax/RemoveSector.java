package org.jlab.cnm.presentation.controller.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.SectorCodeFacade;

/**
 * @author ryans
 */
@WebServlet(
    name = "RemoveSector",
    urlPatterns = {"/ajax/remove-sector"})
public class RemoveSector extends HttpServlet {

  private static final Logger logger = Logger.getLogger(RemoveSector.class.getName());

  @EJB SectorCodeFacade sectorFacade;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String code = request.getParameter("code");

    String stat = "ok";
    String error = null;

    try {
      sectorFacade.removeSector(code);
    } catch (EJBAccessException e) {
      stat = "fail";
      error = "Unable to remove Sector: Not authenticated / authorized (do you need to re-login?)";
    } catch (RuntimeException e) {
      stat = "fail";
      error = "Unable to remove Sector";
      logger.log(Level.SEVERE, "Unable to remove Sector", e);
    }

    response.setContentType("application/json");

    OutputStream out = response.getOutputStream();

    try (JsonGenerator gen = Json.createGenerator(out)) {
      gen.writeStartObject().write("stat", stat);
      if (error != null) {
        gen.write("error", error);
      }
      gen.writeEnd();
    }
  }
}
