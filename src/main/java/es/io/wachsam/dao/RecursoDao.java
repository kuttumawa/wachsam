package es.io.wachsam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Recurso;


@Transactional
public class RecursoDao {
	

	@PersistenceContext
	private EntityManager em;
	

	public Recurso save(Recurso recurso) {
		if (recurso == null)
			return null;
		if(recurso.getId() == null) em.persist(recurso);
		else em.merge(recurso);
	
		return recurso;
	}
	
	public Recurso getRecurso(Long id){
		return em.find(Recurso.class,id);
	}

	

	public void deleteById(Long id) throws Exception {
		Recurso ent = em.find(Recurso.class, id);
		em.remove(ent); 
		
	}

	public boolean existeYaRecurso(Recurso recurso) {
		Query q = em.createQuery(
				"SELECT p FROM Recurso p where p.peligro.id = :peligroid and p.lugar.id =:lugarId", Recurso.class);
		if (q.getResultList()==null || q.getResultList().isEmpty()) return false;
		else return true;
	}

	
	

	
	
	
	
}
