package es.io.wachsam.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;

@Transactional
public class AlertasDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Alert alert) {
		if (alert == null || alert.getId() == null)
			return -1L;
		em.persist(alert);
		return alert.getId();
	}

	public List<Alert> getAll() {
		return em.createQuery("SELECT p FROM Alert p", Alert.class)
				.getResultList();
	}

	public List<Alert> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Alert p where p.lugar LIKE :pais", Alert.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}

	public List<Alert> getAlertas(String texto, String pais, Date fecha,
			String tipo) {
		StringBuilder sb = new StringBuilder("SELECT p FROM Alert p where 1=1");
		if (texto != null && texto.length() > 0) {
			sb.append(" and TRANSLATE( UCASE(texto),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :texto");
			sb.append(" and TRANSLATE( UCASE(text),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :text");
			sb.append(" and TRANSLATE( UCASE(nombre),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :nombre");
		}
		if (pais != null && pais.length() > 0) {
			sb.append(" and TRANSLATE( UCASE(lugar),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :lugar");
		}
		if (fecha != null) {
			sb.append(" and fechaPub < :fecha");
		}
		if (tipo != null && tipo.length() > 3) {
			sb.append(" and TRANSLATE( UCASE(tipo),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :tipo");
		}
		sb.append(" ORDER BY fechaPub DESC");
		Query q = em.createQuery(sb.toString(), Alert.class);
		if (texto != null && texto.length() > 0) {
			q.setParameter("texto", "%" + texto.toUpperCase() + "%");
			q.setParameter("text", "%" + texto.toUpperCase() + "%");
			q.setParameter("nombre", "%" + texto.toUpperCase() + "%");
		}
		if (pais != null && pais.length() > 0) {
			q.setParameter("lugar", "%" + pais.toUpperCase() + "%");
		}
		if (fecha != null) {
			q.setParameter("fecha", fecha);
		}
		if (tipo != null && tipo.length() > 0) {
			q.setParameter("tipo", "%" + tipo.toUpperCase() + "%");
		}
		return q.getResultList();
	}

}
