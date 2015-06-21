package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Factor;


@Transactional
public class FactorDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Factor factor) {
		if (factor == null)
			return -1L;
		if(factor.getId() == null) em.persist(factor);
		else em.merge(factor);
		return factor.getId();
	}
	
	public Factor getFactor(Long id){
		return em.find(Factor.class,id);
	}

	public List<Factor> getAll() {
		return em.createQuery("SELECT p FROM Factor p order by nombre", Factor.class)
				.getResultList();
	}

	
	
	public void deleteById(Long id) throws Exception {
		Factor ent = em.find(Factor.class, id);
		em.remove(ent); 
	}

	
	
	
}
