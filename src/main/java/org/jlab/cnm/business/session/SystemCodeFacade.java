package org.jlab.cnm.business.session;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.smoothness.business.exception.UserFriendlyException;
import org.jlab.smoothness.business.util.ExceptionUtil;

/**
 * @author ryans
 */
@Stateless
public class SystemCodeFacade extends AbstractFacade<SystemCode> {

  private static final Logger logger = Logger.getLogger(SystemCodeFacade.class.getName());

  @PersistenceContext(unitName = "webappPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public SystemCodeFacade() {
    super(SystemCode.class);
  }

  @Override
  @PermitAll
  public List<SystemCode> findAll() {
    return super.findAll(new OrderDirective("sCode"));
  }

  @PermitAll
  public SystemCode findByCode(Character systemCode) {
    TypedQuery<SystemCode> q =
        em.createQuery("select s from SystemCode s where s.sCode = :code", SystemCode.class);

    q.setParameter("code", systemCode);

    List<SystemCode> resultList = q.getResultList();

    SystemCode system = null;

    if (resultList != null && !resultList.isEmpty()) {
      system = resultList.get(0);
    }

    return system;
  }

  /**
   * @param code The Code
   * @param description The description
   * @throws EJBAccessException Explicitly documented here as Servlet should probably catch
   *     insufficient authentication or authorization
   * @throws RuntimeException Explicitly documented here as Servlets should probably catch this one
   *     just in case to avoid ugly errors to users
   * @throws UserFriendlyException Something was done that the user should see and can likely
   *     correct
   */
  @RolesAllowed("cnm-admin")
  public void addSystem(Character code, String description)
      throws EJBAccessException, RuntimeException, UserFriendlyException {
    if (code == null) {
      throw new UserFriendlyException("System code must not be empty");
    }

    if (description == null || description.trim().isEmpty()) {
      throw new UserFriendlyException("Description must not be empty");
    }

    SystemCode system = new SystemCode();

    system.setSCode(code);
    system.setDescription(description);

    try {
      em.persist(system);
      em.flush(); // Required to trigger exceptions
    } catch (RuntimeException e) {
      String message = getSystemFlushMessage(code, e);
      throw new UserFriendlyException(message, e);
    }
  }

  private String getSystemFlushMessage(Character code, RuntimeException e) {
    String message;
    Throwable rootCause = ExceptionUtil.getRootCause(e);
    if (rootCause instanceof SQLException) {
      SQLException dbException = (SQLException) rootCause;
      if (dbException.getMessage().contains("SYSTEM_CODE_AK1")) {
        message = "System code " + code + " already exists";
      } else if (dbException.getMessage().contains("SYSTEM_CODE_CK1")) {
        message = "System code " + code + " is not a single uppercase letter";
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
  public void removeSystem(Character code) {
    SystemCode system = findByCode(code);

    em.remove(system);
  }

  @RolesAllowed("cnm-admin")
  public void editSystem(BigInteger id, Character code, String description)
      throws UserFriendlyException {
    SystemCode system = find(id);

    try {
      system.setSCode(code);
      system.setDescription(description);
      em.flush(); // Required to trigger exceptions
    } catch (RuntimeException e) {
      String message = getSystemFlushMessage(code, e);
      throw new UserFriendlyException(message, e);
    }
  }
}
