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
import org.jlab.cnm.business.session.TypeCodeFacade;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 *
 * @author ryans
 */
@WebServlet(name = "AddType", urlPatterns = {"/ajax/add-type"})
public class AddType extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AddType.class.getName());

    @EJB
    TypeCodeFacade typeFacade;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Character scode = ParamConverter.convertCharacter(request, "scode");
        String vvcode = request.getParameter("vvcode");
        String description = request.getParameter("description");
        String grouping = request.getParameter("grouping");
        
        String stat = "ok";
        String error = null;
        
        try {
            typeFacade.addType(scode, vvcode, description, grouping);
        } catch(UserFriendlyException e) {
            stat = "fail";
            error = "Unable to add Type: " + e.getMessage();
        } catch (EJBAccessException e) {
            stat = "fail";
            error = "Unable to add Type: Not authenticated / authorized (do you need to re-login?)";            
        } catch(RuntimeException e) {
            stat = "fail";
            error = "Unable to add Type";
            logger.log(Level.SEVERE, "Unable to add Type", e);
        }
        
        response.setContentType("application/json");

        OutputStream out = response.getOutputStream();
        
        try (JsonGenerator gen = Json.createGenerator(out)) {
            gen.writeStartObject()
                    .write("stat", stat);
            if(error != null) {
                gen.write("error", error);
            }
            gen.writeEnd();
        }
    }

}
