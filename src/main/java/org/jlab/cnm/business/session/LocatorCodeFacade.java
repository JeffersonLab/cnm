package org.jlab.cnm.business.session;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.jlab.cnm.persistence.entity.LocatorCode;
import org.jlab.cnm.persistence.entity.SectorCode;

/**
 *
 * @author ryans
 */
@Stateless
public class LocatorCodeFacade extends AbstractFacade<LocatorCode> {
    @PersistenceContext(unitName = "webappPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocatorCodeFacade() {
        super(LocatorCode.class);
    }
    
    @Override
    public List<LocatorCode> findAll() {
        return super.findAll(new OrderDirective("yyCode"));
    }    

    @PermitAll
    public LocatorCode findByCode(String code) {
        TypedQuery<LocatorCode> q = em.createQuery("select l from LocatorCode l where l.yyCode = :code", LocatorCode.class);
        
        q.setParameter("code", code);
        
        List<LocatorCode> resultList = q.getResultList();
        LocatorCode result = null;
        
        if(resultList != null && !resultList.isEmpty()) {
            result = resultList.get(0);
        }
        
        return result;
    }

    @PermitAll
    public List<LocatorCode> findBySector(SectorCode sector) {
        TypedQuery<LocatorCode> q = em.createQuery("select l from LocatorCode l where l.sectorCode.sectorCodeId = :sectorCodeId", LocatorCode.class);
        
        q.setParameter("sectorCodeId", sector.getSectorCodeId());
        
        return q.getResultList();
    }
}
