package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Mitigacion;

@Transactional
public class MitigacionDao {

	@PersistenceContext
	private EntityManager em;

	public Mitigacion save(Mitigacion mitigacion) {
		if (mitigacion == null)
			return null;
		if (mitigacion.getId() == null)
			em.persist(mitigacion);
		else
			em.merge(mitigacion);

		return mitigacion;
	}

	public Mitigacion getMitigacion(Long id) {
		return em.find(Mitigacion.class, id);
	}

	public List<Mitigacion> getAll() {
		return em.createQuery(
				"SELECT p FROM Mitigacion p order by p.lugar.nivel",
				Mitigacion.class).getResultList();
	}

	public List<Mitigacion> getMitigacionesFromPeligro(Long peligroid) {
		Query q = em.createQuery(
				"SELECT p FROM Mitigacion p where p.peligro.id = :peligroid",
				Mitigacion.class);
		q.setParameter("peligroid", peligroid);
		return q.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Mitigacion ent = em.find(Mitigacion.class, id);
		em.remove(ent);

	}

	public boolean existeYaMitigacion(Mitigacion mitigacion) {
		Query q = em
				.createQuery(
						"SELECT p FROM Mitigacion p where p.peligro.id = :peligroid and p.factor.id =:factorId",
						Mitigacion.class);
		q.setParameter("peligroid", mitigacion.getPeligro().getId());
		q.setParameter("factorId", mitigacion.getFactor().getId());
		if (q.getResultList() == null || q.getResultList().isEmpty())
			return false;
		else
			return true;
	}

}
