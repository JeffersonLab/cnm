package org.jlab.cnm.business.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
