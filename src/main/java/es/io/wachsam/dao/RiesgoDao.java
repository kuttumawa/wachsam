package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Riesgo;


@Transactional
public class RiesgoDao {
	final String CACHE="ioCacheRiesgo";
	private CacheManager cacheManager;

	@PersistenceContext
	private EntityManager em;
	
	@CacheEvict(CACHE)
	public Riesgo save(Riesgo riesgo) {
		if (riesgo == null)
			return null;
		if(riesgo.getId() == null) em.persist(riesgo);
		else em.merge(riesgo);
		evictCache();
		return riesgo;
	}
	
	public Riesgo getRiesgo(Long id){
		return em.find(Riesgo.class,id);
	}
	@Cacheable(CACHE)
	public List<Riesgo> getAll() {
		return em.createQuery("SELECT p FROM Riesgo p", Riesgo.class)
				.getResultList();
	}
	@Cacheable(CACHE)
	public List<Riesgo> getRiesgosFromPeligro(Long idpais) {
		Query q = em.createQuery(
				"SELECT p FROM Riesgo p where p.lugar.id = :lugarid", Riesgo.class);
		q.setParameter("lugar",idpais);
		return q.getResultList();
	}
	@CacheEvict(CACHE)
	public void deleteById(Long id) throws Exception {
		Riesgo ent = em.find(Riesgo.class, id);
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

	public List<Riesgo> getRiesgosFromPeligro(long parseLong) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
