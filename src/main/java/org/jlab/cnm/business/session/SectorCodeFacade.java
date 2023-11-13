package org.jlab.cnm.business.session;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.business.util.ExceptionUtil;

/**
 *
 * @author ryans
 */
@Stateless
public class SectorCodeFacade extends AbstractFacade<SectorCode> {
    private static final Logger logger = Logger.getLogger(SectorCodeFacade.class.getName());      
    
    @PersistenceContext(unitName = "webappPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SectorCodeFacade() {
        super(SectorCode.class);
    }    

    @PermitAll
    public SectorCode findByCode(String code) {
        TypedQuery<SectorCode> q = em.createQuery("select s from SectorCode s where s.xxCode = :code", SectorCode.class);
        
        q.setParameter("code", code);
        
        List<SectorCode> resultList = q.getResultList();
        SectorCode result = null;
        
        if(resultList != null && !resultList.isEmpty()) {
            result = resultList.get(0);
        }
        
        return result;
    }

    @RolesAllowed("cnmadm")
    public void addSector(String code, String description, String grouping) throws UserFriendlyException {
        if (code == null || code.trim().isEmpty()) {
            throw new UserFriendlyException("System code must not be empty");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new UserFriendlyException("Description must not be empty");
        }

        if(grouping != null && grouping.trim().isEmpty()) {
            grouping = null;
        }        
        
        SectorCode sector = new SectorCode();

        sector.setXxCode(code);
        sector.setDescription(description);
        sector.setGrouping(grouping);

        try {
            em.persist(sector);
            em.flush(); // Required to trigger exceptions
        } catch (RuntimeException e) {
            String message = getSectorFlushMessage(code, e);
            throw new UserFriendlyException(message, e);
        }
    }
    
    private String getSectorFlushMessage(String code, RuntimeException e) {
        String message;
        Throwable rootCause = ExceptionUtil.getRootCause(e);
        if (rootCause instanceof SQLException) {
            SQLException dbException = (SQLException) rootCause;
            if (dbException.getMessage().contains("SECTOR_CODE_AK1")) {
                message = "Sector code " + code + " already exists";
            } else if (dbException.getMessage().contains("SECTOR_CODE_CK1")) {
                message = "Sector code " + code + " is not a pair of uppercase letters or numbers";
            } else {
                message = "Database error";
            }
        } else {
            message = "Internal error";
            logger.log(Level.WARNING, message, e);
        }            
        
        return message;
    }    

    @RolesAllowed("cnmadm")
    public void removeSector(String code) {
        SectorCode sector = findByCode(code);
        
        em.remove(sector);
    }

    @RolesAllowed("cnmadm")
    public void editSector(BigInteger id, String code, String description, String grouping) throws UserFriendlyException {
        SectorCode sector = find(id);

        if(grouping != null && grouping.trim().isEmpty()) {
            grouping = null;
        }        
        
        try {
            sector.setXxCode(code);
            sector.setDescription(description);
            sector.setGrouping(grouping);
            em.flush(); // Required to trigger exceptions
        } catch(RuntimeException e) {
            String message = getSectorFlushMessage(code, e);
            throw new UserFriendlyException(message, e);            
        }
    }

    @PermitAll
    public List<SectorCode> filterList(String xxCode, String grouping, int offset, int max) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<SectorCode> cq = cb.createQuery(SectorCode.class);
        Root<SectorCode> root = cq.from(SectorCode.class);
        cq.select(root);
        
        List<Predicate> filters = new ArrayList<>();

        if (xxCode != null && !xxCode.trim().isEmpty()) {
            filters.add(cb.like(cb.upper(root.get("xxCode")), xxCode.toUpperCase()));
        }        
        
        if (grouping != null && !grouping.trim().isEmpty()) {
            filters.add(cb.equal(root.<String>get("grouping"), grouping));
        }

        if (!filters.isEmpty()) {
            cq.where(cb.and(filters.toArray(new Predicate[]{})));
        }
        
        List<Order> orders = new ArrayList<>();
        Path p0 = root.get("grouping");
        Order o0 = cb.asc(p0);
        orders.add(o0);
        Path p1 = root.get("xxCode");
        Order o1 = cb.asc(p1);
        orders.add(o1);
        cq.orderBy(orders);
        return getEntityManager().createQuery(cq).setFirstResult(offset).setMaxResults(max).getResultList();
    }

    @PermitAll
    public long countList(String xxCode, String grouping) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<SectorCode> root = cq.from(SectorCode.class);
        
        List<Predicate> filters = new ArrayList<>();

        if (xxCode != null && !xxCode.trim().isEmpty()) {
            filters.add(cb.like(cb.upper(root.get("xxCode")), xxCode.toUpperCase()));
        }        
        
        if (grouping != null && !grouping.trim().isEmpty()) {
            filters.add(cb.equal(root.<String>get("grouping"), grouping));
        }      
        
        if (!filters.isEmpty()) {
            cq.where(cb.and(filters.toArray(new Predicate[]{})));
        }

        cq.select(cb.count(root));
        TypedQuery<Long> q = getEntityManager().createQuery(cq);
        return q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @PermitAll
    public List<String> findGroupingList() {
        Query q = em.createNativeQuery("select distinct grouping from sector_code where grouping is not null order by grouping asc");
        
        List<Object> resultList = q.getResultList();
        
        List<String> groupingList = new ArrayList<>();
        
        if(resultList != null) {
            for(Object row: resultList) {
                groupingList.add((String)row);
            }
        }
        
        return groupingList;
    }
}
