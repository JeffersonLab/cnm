package org.jlab.cnm.presentation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jlab.cnm.business.session.TranslatorService;
import org.jlab.smoothness.business.util.ObjectUtil;

/**
 *
 * @author ryans
 */
@WebServlet(name = "Translate", urlPatterns = {"/translate"})
public class Translate extends HttpServlet {

    private static final Logger logger = Logger.getLogger(
            Translate.class.getName());

    @EJB
    TranslatorService translator;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");
        
        String meaning = translator.translate(query);
        
        response.setContentType("application/json");

        OutputStream out = response.getOutputStream();

        try (JsonGenerator gen = Json.createGenerator(out)) {
            gen.writeStartObject()
                    .write("meaning", ObjectUtil.coalesce(meaning, ""));
            gen.writeEnd();
        }
    }

}
