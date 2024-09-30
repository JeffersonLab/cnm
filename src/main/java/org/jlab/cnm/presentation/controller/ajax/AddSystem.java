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
import org.jlab.cnm.business.session.SystemCodeFacade;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 * @author ryans
 */
@WebServlet(
    name = "AddSystem",
    urlPatterns = {"/ajax/add-system"})
public class AddSystem extends HttpServlet {

  private static final Logger logger = Logger.getLogger(AddSystem.class.getName());

  @EJB SystemCodeFacade systemFacade;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Character code = ParamConverter.convertCharacter(request, "code");
    String description = request.getParameter("description");

    String stat = "ok";
    String error = null;

    try {
      systemFacade.addSystem(code, description);
    } catch (UserFriendlyException e) {
      stat = "fail";
      error = "Unable to add System: " + e.getMessage();
    } catch (EJBAccessException e) {
      stat = "fail";
      error = "Unable to add System: Not authenticated / authorized (do you need to re-login?)";
    } catch (RuntimeException e) {
      stat = "fail";
      error = "Unable to add System";
      logger.log(Level.SEVERE, "Unable to add System", e);
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
