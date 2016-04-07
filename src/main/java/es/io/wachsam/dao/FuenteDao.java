package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Fuente;


@Transactional
public class FuenteDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Fuente fuente) {
		if (fuente == null)
			return -1L;
		if(fuente.getId() == null) em.persist(fuente);
		else em.merge(fuente);
		return fuente.getId();
	}
	
	public Fuente getFuente(Long id){
		return em.find(Fuente.class,id);
	}

	public List<Fuente> getAll() {
		return em.createQuery("SELECT p FROM Fuente p order by nombre", Fuente.class)
				.getResultList();
	}

	
	
	public void deleteById(Long id) throws Exception {
		Fuente ent = em.find(Fuente.class, id);
		em.remove(ent); 
	}

	
	
	
}
