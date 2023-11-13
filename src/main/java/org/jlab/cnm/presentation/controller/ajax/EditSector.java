package org.jlab.cnm.presentation.controller.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
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
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.presentation.util.ParamConverter;

/**
 *
 * @author ryans
 */
@WebServlet(name = "EditSector", urlPatterns = {"/ajax/edit-sector"})
public class EditSector extends HttpServlet {

    private static final Logger logger = Logger.getLogger(EditSector.class.getName());

    @EJB
    SectorCodeFacade sectorFacade;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BigInteger id = ParamConverter.convertBigInteger(request, "id");
        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String grouping = request.getParameter("grouping");
        
        String stat = "ok";
        String error = null;
        
        try {
            sectorFacade.editSector(id, code, description, grouping);            
        } catch(UserFriendlyException e) {
            stat = "fail";
            error = "Unable to edit Sector: " + e.getMessage();
        } catch (EJBAccessException e) {
            stat = "fail";
            error = "Unable to edit Sector: Not authenticated / authorized (do you need to re-login?)";
        } catch(RuntimeException e) {
            stat = "fail";
            error = "Unable to edit Sector";
            logger.log(Level.SEVERE, "Unable to edit Sector", e);
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
