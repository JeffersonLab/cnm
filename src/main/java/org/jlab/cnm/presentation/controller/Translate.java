package org.jlab.cnm.presentation.controller;

import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;
import org.jlab.cnm.business.session.TranslatorService;
import org.jlab.smoothness.business.util.ObjectUtil;

/**
 * @author ryans
 */
@WebServlet(
    name = "Translate",
    urlPatterns = {"/translate"})
public class Translate extends HttpServlet {

  private static final Logger logger = Logger.getLogger(Translate.class.getName());

  @EJB TranslatorService translator;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String query = request.getParameter("q");

    String meaning = translator.translate(query);

    response.setContentType("application/json");

    OutputStream out = response.getOutputStream();

    try (JsonGenerator gen = Json.createGenerator(out)) {
      gen.writeStartObject().write("meaning", ObjectUtil.coalesce(meaning, ""));
      gen.writeEnd();
    }
  }
}
