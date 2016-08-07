package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;


@Transactional
public class PeligroDao {
	final String CACHE="ioCachePeligro";
	private CacheManager cacheManager;

	@PersistenceContext
	private EntityManager em;
	
	@CacheEvict(CACHE)
	public Peligro save(Peligro peligro) {
		if (peligro == null)
			return null;
		if(peligro.getId() == null) em.persist(peligro);
		else em.merge(peligro);
		evictCache();
		return peligro;
	}
	
	public Peligro getPeligro(Long id){
		return em.find(Peligro.class,id);
	}
	@Cacheable(CACHE)
	public List<Peligro> getAll() {
		return em.createQuery("SELECT p FROM Peligro p order by nombre", Peligro.class)
				.getResultList();
	}
	@Cacheable(CACHE)
	public List<Peligro> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Peligro p where p.peligro LIKE :pais", Peligro.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}
	@CacheEvict(CACHE)
	public void deleteById(Long id) throws Exception {
		Peligro ent = em.find(Peligro.class, id);
		em.remove(ent); 
		evictCache();
	}
	public void evictCache(){
		cacheManager.getCache(CACHE).clear();
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public List<Peligro> getPeligroFromTexto(String nombre) {
		Query q = em.createQuery(
				"SELECT p FROM Peligro p where p.nombre = :nombre", Peligro.class);
		q.setParameter("nombre",nombre);
		return q.getResultList();
	}
	
	
	
}
