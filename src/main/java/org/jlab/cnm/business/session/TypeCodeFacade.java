package org.jlab.cnm.business.session;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.business.util.ExceptionUtil;

/**
 *
 * @author ryans
 */
@Stateless
public class TypeCodeFacade extends AbstractFacade<TypeCode> {
    private static final Logger logger = Logger.getLogger(TypeCodeFacade.class.getName());    
    
    @PersistenceContext(unitName = "webappPU")
    private EntityManager em;    
    
    @EJB
    SystemCodeFacade systemFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeCodeFacade() {
        super(TypeCode.class);
    }
    
    @PermitAll
    public List<TypeCode> filterList(Character sCode, String vvCode, String grouping, int offset, int max) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TypeCode> cq = cb.createQuery(TypeCode.class);
        Root<TypeCode> root = cq.from(TypeCode.class);
        Join<TypeCode, SystemCode> system = root.join("systemCode");
        cq.select(root);
        
        List<Predicate> filters = new ArrayList<>();

        if (sCode != null) {
            filters.add(cb.equal(system.<Character>get("sCode"), sCode));
        }

        if(vvCode != null && !vvCode.trim().isEmpty()) {
            filters.add(cb.like(cb.upper(root.get("vvCode")), vvCode.toUpperCase()));
        }
        
        if (grouping != null && !grouping.trim().isEmpty()) {
            filters.add(cb.equal(root.<String>get("grouping"), grouping));
        }        
        
        if (!filters.isEmpty()) {
            cq.where(cb.and(filters.toArray(new Predicate[]{})));
        }
        
        List<Order> orders = new ArrayList<>();
        Path p0 = system.get("sCode");
        Order o0 = cb.asc(p0);
        orders.add(o0);
        Path p1 = root.get("grouping");
        Order o1 = cb.asc(p1);
        orders.add(o1);
        Path p2 = root.get("vvCode");
        Order o2 = cb.asc(p2);
        orders.add(o2);        
        cq.orderBy(orders);
        return getEntityManager().createQuery(cq).setFirstResult(offset).setMaxResults(max).getResultList();
    }    

    @PermitAll
    public long countList(Character sCode, String vvCode, String grouping) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<TypeCode> root = cq.from(TypeCode.class);
        Join<TypeCode, SystemCode> system = root.join("systemCode");
        
        List<Predicate> filters = new ArrayList<>();

        if (sCode != null) {
            filters.add(cb.equal(system.<Character>get("sCode"), sCode));
        }      
        
        if(vvCode != null && !vvCode.trim().isEmpty()) {
            filters.add(cb.like(cb.upper(root.get("vvCode")), vvCode.toUpperCase()));
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
    
    @PermitAll
    public List<TypeCode> findBySystem(SystemCode system) {
        TypedQuery<TypeCode> q = em.createQuery("select t from TypeCode t where t.systemCode.systemCodeId = :systemCodeId", TypeCode.class);
        
        q.setParameter("systemCodeId", system.getSystemCodeId());
        
        return q.getResultList();
    }

    @PermitAll
    public TypeCode findByCode(Character systemCode, String typeCode) {
        TypedQuery<TypeCode> q = em.createQuery("select t from TypeCode t where t.systemCode.sCode = :systemCode and t.vvCode = :typeCode", TypeCode.class);
        
        q.setParameter("systemCode", systemCode);
        q.setParameter("typeCode", typeCode);
        
        List<TypeCode> resultList = q.getResultList();
        TypeCode result = null;
        
        if(resultList != null && !resultList.isEmpty()) {
            result = resultList.get(0);
        }
        
        return result;
    }

    @RolesAllowed("cnm-admin")
    public void addType(Character scode, String vvcode, String description, String grouping) throws UserFriendlyException {
        if (scode == null) {
            throw new UserFriendlyException("System code must not be empty");
        }

        if (vvcode == null || vvcode.trim().isEmpty()) {
            throw new UserFriendlyException("Type code must not be empty");
        }        
        
        if (description == null || description.trim().isEmpty()) {
            throw new UserFriendlyException("Description must not be empty");
        }

        TypeCode type = new TypeCode();

        SystemCode system = systemFacade.findByCode(scode);
        
        if(system == null) {
            throw new UserFriendlyException("System code " + scode + " does not exist");
        }
        
        if(grouping != null && grouping.trim().isEmpty()) {
            grouping = null;
        }        
        
        type.setSystemCode(system);
        type.setVvCode(vvcode);
        type.setDescription(description);
        type.setGrouping(grouping);

        try {
            em.persist(type);
            em.flush(); // Required to trigger exceptions
        } catch (RuntimeException e) {
            String message = getTypeFlushMessage(scode, vvcode, e);
            throw new UserFriendlyException(message, e);
        }
    }
    
    private String getTypeFlushMessage(Character scode, String vvcode, RuntimeException e) {
        String message;
        Throwable rootCause = ExceptionUtil.getRootCause(e);
        if (rootCause instanceof SQLException) {
            SQLException dbException = (SQLException) rootCause;
            if (dbException.getMessage().contains("TYPE_CODE_AK1")) {
                message = "Type code " + scode + vvcode + " already exists";
            } else if (dbException.getMessage().contains("TYPE_CODE_CK1")) {
                message = "Type code " + vvcode + " is not a pair of uppercase letters or numbers";
            } else {
                message = "Database error";
            }
        } else {
            message = "Internal error";
            logger.log(Level.WARNING, message, e);
        }
        return message;
    }    

    @RolesAllowed("cnm-admin")
    public void removeType(Character scode, String vvcode) {        
        TypeCode type = this.findByCode(scode, vvcode);
        
        em.remove(type);
    }

    @RolesAllowed("cnm-admin")
    public void editType(BigInteger id, Character scode, String vvcode, String description, String grouping) throws UserFriendlyException {
        TypeCode type = find(id);
        SystemCode system = systemFacade.findByCode(scode);
        
        if(system == null) {
            throw new UserFriendlyException("System code " + scode + " does not exist");
        }
        
        if(grouping != null && grouping.trim().isEmpty()) {
            grouping = null;
        }
        
        try {
            type.setSystemCode(system);
            type.setVvCode(vvcode);
            type.setDescription(description);
            type.setGrouping(grouping);
            em.flush(); // Required to trigger exceptions
        } catch(RuntimeException e) {
            String message = getTypeFlushMessage(scode, vvcode, e);
            throw new UserFriendlyException(message, e);            
        }
    }
    
    @RolesAllowed("cnm-admin")
    public void editAttributes(BigInteger id, String jsonAttributes) throws UserFriendlyException {
        TypeCode type = find(id);
        
        if(type == null) {
            throw new UserFriendlyException("Type with ID " + id + " not found");
        }
        
        type.setJsonAttributes(jsonAttributes);
    }    
    
    @PermitAll
    @SuppressWarnings("unchecked")
    public List<String> findGroupingList(Character sCode) {
        String sql = "select distinct grouping from type_code where grouping is not null order by grouping asc";
       
        if(sCode != null) {
            sql = "select distinct grouping from type_code t left join system_code s on (t.system_code_id = s.system_code_id) where s.s_code = ? and grouping is not null order by grouping asc";
        }
        
        Query q = em.createNativeQuery(sql);
        
        if(sCode != null) {
            q.setParameter(1, sCode);
        }
        
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
