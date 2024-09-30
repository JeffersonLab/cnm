package org.jlab.cnm.business.session;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;

/**
 * @author ryans
 */
@Stateless
public class TranslatorService {

  @EJB SystemCodeFacade systemFacade;

  @EJB TypeCodeFacade typeFacade;

  @EJB SectorCodeFacade sectorFacade;

  @PermitAll
  public String translate(String query) {
    String meaning = "Unknown";

    if (query != null && !query.isEmpty()) {
      query = query.toUpperCase();

      Character sCode = query.charAt(0);

      SystemCode system = systemFacade.findByCode(sCode);

      String systemDescription = "Unknown System";

      if (system != null) {
        systemDescription = system.getDescription();
      }

      meaning = systemDescription;

      if (query.length() >= 3) {
        String typeCode = query.substring(1, 3);

        TypeCode type = null;

        if (system != null) {
          type = typeFacade.findByCode(system.getSCode(), typeCode);
        }

        String typeDescription = "Unknown Type";

        if (type != null) {
          typeDescription = type.getDescription();
        }

        meaning = meaning + " + " + typeDescription;
      }

      if (query.length() >= 5) {
        String sectorCode = query.substring(3, 5);

        SectorCode sector = sectorFacade.findByCode(sectorCode);

        String sectorDescription = "Unknown Sector";

        if (sector != null) {
          sectorDescription = sector.getDescription();
        }

        meaning = meaning + " + " + sectorDescription;
      }
    }

    return meaning;
  }
}
