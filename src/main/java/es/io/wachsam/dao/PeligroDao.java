package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Peligro;


@Transactional
public class PeligroDao {

	@PersistenceContext
	private EntityManager em;
	
	@CacheEvict("ioCachePeligro")
	public Peligro save(Peligro peligro) {
		if (peligro == null)
			return null;
		if(peligro.getId() == null) em.persist(peligro);
		else em.merge(peligro);
		return peligro;
	}
	
	public Peligro getPeligro(Long id){
		return em.find(Peligro.class,id);
	}
	@Cacheable("ioCachePeligro")
	public List<Peligro> getAll() {
		return em.createQuery("SELECT p FROM Peligro p order by nombre", Peligro.class)
				.getResultList();
	}
	@Cacheable("ioCachePeligro")
	public List<Peligro> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Peligro p where p.peligro LIKE :pais", Peligro.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}
	@CacheEvict("ioCachePeligro")
	public void deleteById(Long id) throws Exception {
		Peligro ent = em.find(Peligro.class, id);
		em.remove(ent); 
	}

	
	
	
}
