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

@Transactional
public class LugarDao {
	final String CACHE="ioCacheLugar";
	private CacheManager cacheManager;
	@PersistenceContext
	private EntityManager em;
	@CacheEvict(CACHE)
	public Lugar save(Lugar lugar) {
		if (lugar == null)
			return null;
		if(lugar.getId() == null) em.persist(lugar);
		else em.merge(lugar);
		evictCache();
		return lugar;
	}
	
	public Lugar getLugar(Long id){
		return em.find(Lugar.class,id);
	}
	@Cacheable(CACHE)
	public List<Lugar> getAll() {
		return em.createQuery("SELECT p FROM Lugar p order by p.nombre", Lugar.class)
				.getResultList();
	}
	@Cacheable(CACHE)
	public List<Lugar> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Lugar p where p.lugar LIKE :pais", Lugar.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}
	@CacheEvict(CACHE)
	public void deleteById(Long id) throws Exception {
		Lugar ent = em.find(Lugar.class, id);
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
	
	
}
