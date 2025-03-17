package org.jlab.cnm.presentation.controller.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 * @author ryans
 */
@WebServlet(
    name = "FilterTypeGroupingBySystem",
    urlPatterns = {"/ajax/filter-type-grouping-by-system"})
public class FilterTypeGroupingBySystem extends HttpServlet {

  private static final Logger logger = Logger.getLogger(FilterTypeGroupingBySystem.class.getName());

  @EJB TypeCodeFacade typeFacade;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String stat = "ok";
    String error = null;
    List<String> groupingList = null;

    try {
      Character code = ParamConverter.convertCharacter(request, "code");

      groupingList = typeFacade.findGroupingList(code);
    } catch (UserFriendlyException e) {
      stat = "fail";
      error = "Unable to edit Type: " + e.getUserMessage();
    } catch (RuntimeException e) {
      stat = "fail";
      error = "Unable to filter grouping list";
      logger.log(Level.SEVERE, "Unable to filter grouping list", e);
    }

    response.setContentType("application/json");

    OutputStream out = response.getOutputStream();

    try (JsonGenerator gen = Json.createGenerator(out)) {
      gen.writeStartObject().write("stat", stat);
      if (error != null) {
        gen.write("error", error);
      }
      if (groupingList != null) {
        gen.writeStartArray("options");
        for (String grouping : groupingList) {
          gen.writeStartObject().write("name", grouping).write("value", grouping).writeEnd();
        }
        gen.writeEnd();
      }
      gen.writeEnd();
    }
  }
}
