package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Lugar;

@Transactional
public class LugarDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Lugar lugar) {
		if (lugar == null || lugar.getId() == null)
			return -1L;
		em.persist(lugar);
		return lugar.getId();
	}
	
	public Lugar getLugar(Long id){
		return em.find(Lugar.class,id);
	}

	public List<Lugar> getAll() {
		return em.createQuery("SELECT p FROM Lugar p", Lugar.class)
				.getResultList();
	}

	public List<Lugar> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Lugar p where p.lugar LIKE :pais", Lugar.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}

	
	
	
}
