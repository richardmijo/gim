/**
 * 
 */
package org.gob.gim.coercive.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.gob.gim.coercive.dto.criteria.NotificationInfractionSearchCriteria;
import org.gob.gim.coercive.dto.criteria.PaymentInfractionsSearchCriteria;
import org.gob.gim.common.service.CrudService;

import ec.gob.gim.coercive.model.infractions.HistoryStatusNotification;
import ec.gob.gim.coercive.model.infractions.NotificationInfractions;
import ec.gob.gim.coercive.model.infractions.PaymentInfraction;

/**
 * @author René
 *
 */
@Stateless(name = "PaymentInfractionsService")
public class PaymentInfractionsServiceBean implements PaymentInfractionsService {

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	CrudService crudService;

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentInfraction> getPaymentsByCriteria(
			PaymentInfractionsSearchCriteria criteria, Long statusid) {

		String qry = "SELECT pay FROM PaymentInfraction pay "
				+ "JOIN FETCH pay.infraction "
				+ "LEFT JOIN FETCH pay.finantialInstitution "
				+ "JOIN FETCH pay.cashier" 
				+ " WHERE 1=1 "
				+ " and pay.date between :from and :until "
				+ " and pay.status.id=:statusid";
		qry += " ORDER BY pay.date desc, pay.time DESC ";

		Query query = this.entityManager.createQuery(qry);
		query.setParameter("from", criteria.getFrom());
		query.setParameter("until", criteria.getUntil());
		query.setParameter("statusid", statusid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentInfraction> findPaymentInfractionByCriteria(
			PaymentInfractionsSearchCriteria criteria, Integer firstRow,
			Integer numberOfRows) {

		String qry = "SELECT pay FROM PaymentInfraction pay "
				+ " WHERE 1=1 " + " and pay.date between :from and :until "
				+ " and pay.status.id=:statusid";
		qry += " ORDER BY pay.date desc, pay.time DESC ";

		Query query = this.entityManager.createQuery(qry);
		query.setParameter("from", criteria.getFrom());
		query.setParameter("until", criteria.getUntil());
		query.setParameter("statusid", criteria.getStatusid());

		query.setFirstResult(firstRow);
		query.setMaxResults(numberOfRows);

		return query.getResultList();
	}

	@Override
	public PaymentInfraction findObjectById(Long id) {
		Query query = entityManager
				.createQuery("SELECT pay FROM PaymentInfraction pay "
						+ "WHERE pay.id=:id");
		query.setParameter("id", id);
		return (PaymentInfraction) query.getSingleResult();
	}

	@Override
	public Integer findPaymentsNumber(PaymentInfractionsSearchCriteria criteria) {
		String qry = "SELECT count(DISTINCT pay.id) FROM PaymentInfraction pay "
				+ " WHERE 1=1 "
				+ " and pay.date between :from and :until "
				+ " and pay.status.id=:statusid";

		Query query = this.entityManager.createQuery(qry);
		query.setParameter("from", criteria.getFrom());
		query.setParameter("until", criteria.getUntil());
		query.setParameter("statusid", criteria.getStatusid());
		Long size = (Long) query.getSingleResult();

		return size.intValue();
	}

	@Override
	public BigDecimal getTotalByCriteriaSearch(
			PaymentInfractionsSearchCriteria criteria) {
		
		String qry = "SELECT SUM(pay.value) FROM PaymentInfraction pay "
				+ " WHERE 1=1 " + " and pay.date between :from and :until "
				+ " and pay.status.id=:statusid";

		Query query = this.entityManager.createQuery(qry);
		query.setParameter("from", criteria.getFrom());
		query.setParameter("until", criteria.getUntil());
		query.setParameter("statusid", criteria.getStatusid());
		
		return (BigDecimal) query.getSingleResult();
		
	}
}
