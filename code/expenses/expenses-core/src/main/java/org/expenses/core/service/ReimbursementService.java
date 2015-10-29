package org.expenses.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.expenses.core.model.Conference;
import org.expenses.core.model.Currency;
import org.expenses.core.model.Reimbursement;
import org.expenses.core.model.User;

/**
 * Transactional service for Reimbursement entities.
 * <p/>
 * This class provides CRUD functionality for all Reimbursement entities.
 */

@Transactional
public class ReimbursementService extends AbstractService<Reimbursement>
{

   public ReimbursementService()
   {
      super(Reimbursement.class);
   }

   public Reimbursement findById(Long id)
   {

      TypedQuery<Reimbursement> findByIdQuery = getEntityManager().createQuery(
               "SELECT DISTINCT r FROM Reimbursement r LEFT JOIN FETCH r.expenses LEFT JOIN FETCH r.user LEFT JOIN FETCH r.conference WHERE r.id =:entityId ORDER BY r.id",
               Reimbursement.class);
      findByIdQuery.setParameter("entityId", id);
      Reimbursement entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      return entity;
   }

   @Override
   protected Predicate[] getSearchPredicates(Root<Reimbursement> root, Reimbursement example)
   {

      CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      Currency currency = example.getCurrency();
      if (currency != null)
      {
         predicatesList.add(builder.equal(root.get("currency"), currency));
      }
      User user = example.getUser();
      if (user != null)
      {
         predicatesList.add(builder.equal(root.get("user"), user));
      }
      Conference conference = example.getConference();
      if (conference != null)
      {
         predicatesList
                  .add(builder.equal(root.get("conference"), conference));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }
}
