package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Peligro;


@Transactional
public class PeligroDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Peligro peligro) {
		if (peligro == null)
			return -1L;
		if(peligro.getId() == null) em.persist(peligro);
		else em.merge(peligro);
		return peligro.getId();
	}
	
	public Peligro getPeligro(Long id){
		return em.find(Peligro.class,id);
	}

	public List<Peligro> getAll() {
		return em.createQuery("SELECT p FROM Peligro p order by nombre", Peligro.class)
				.getResultList();
	}

	public List<Peligro> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Peligro p where p.peligro LIKE :pais", Peligro.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}
	
	public void deleteById(Long id) throws Exception {
		Peligro ent = em.find(Peligro.class, id);
		em.remove(ent); 
	}

	
	
	
}
