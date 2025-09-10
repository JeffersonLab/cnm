package org.jlab.cnm.business.session;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.jlab.cnm.persistence.entity.ElementCode;

/**
 * @author ryans
 */
@Stateless
public class ElementCodeFacade extends AbstractFacade<ElementCode> {
  @PersistenceContext(unitName = "webappPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public ElementCodeFacade() {
    super(ElementCode.class);
  }

  @Override
  public List<ElementCode> findAll() {
    return super.findAll(new OrderDirective("zzCode"));
  }
}
