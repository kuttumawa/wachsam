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
	public List<Lugar> getAllPorPaísOrdenadosPorNivelyNombre() {
		return em.createQuery("SELECT p FROM Lugar p order by p.nivel,p.nombre", Lugar.class)
				.getResultList();
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
	public List<Lugar> getLugarFromTexto(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Lugar p where p.nombre = :pais", Lugar.class);
		q.setParameter("pais",pais);
		return q.getResultList();
	}

	public List<Long> getLugarFromISO_3166_1_alpha2(String code) {
		String query="SELECT lugar.id FROM lugar,data where lugar.id=data.objetoid and value='"+code+"' and data.tag_id=(select id from tag where tag.alias='ISO_3166_1_alpha2')";
		Query q = em.createNativeQuery(query);
		return q.getResultList();
	}
	
	public List<Long> getLugarFromISO_3166_1_alpha3(String code) {
		String query="SELECT lugar.id FROM lugar,data where lugar.id=data.objetoid and value='"+code+"' and data.tag_id=(select id from tag where tag.alias='ISO_3166_1_alpha3')";
		Query q = em.createNativeQuery(query);
		return q.getResultList();
	}
	
	public List<Long> getLugarFromISO_3166_1_num(String code) {
		String query="SELECT lugar.id FROM lugar,data where lugar.id=data.objetoid and value='"+code+"' and data.tag_id=(select id from tag where tag.alias='ISO_3166_1_num')";
		Query q = em.createNativeQuery(query);
		return q.getResultList();
	}
	
	public 	List<Lugar> getLugarHijos(Lugar lugar){
		Query q = em.createQuery(
				"SELECT p FROM Lugar p where p.padre1 = :lugar", Lugar.class);
		q.setParameter("lugar",lugar);
		return q.getResultList();
	}
	
}
