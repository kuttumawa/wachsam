package es.io.wachsam.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.validator.GenericValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Sitio;

@Transactional
public class SitioDao {
final String SITIO_CACHE="ioCacheSitio";
private CacheManager cacheManager;

	@PersistenceContext
	private EntityManager em;
	@CacheEvict(SITIO_CACHE)
	public Sitio save(Sitio sitio) {		
		if (sitio == null)
			return null;
		if (sitio.getId() == null)
			em.persist(sitio);
		else
			em.merge(sitio);
		evictCache();
		return sitio;
	}

	public Sitio getSitio(Long id) {
		return em.find(Sitio.class, id);
	}

	/**
	 * @param vector
	 *            [tag1,tag2,tag3,lugarId,subjectId,eventoId]
	 * @return
	 */
	@Cacheable(SITIO_CACHE)
	public List<Sitio> getSitios(Long[] vector) {
		String sql = "SELECT p FROM Sitio p where tag1=:tag1 and tag2=:tag2 and tag3=:tag3 and lugarId=:lugarId "
				+ "and subjectId=:subjectId and eventoId=:eventoId";
		Query q = em.createQuery(sql, Sitio.class);
		for (int i = 0; i < vector.length; i++) {
			q.setParameter(i, vector[i]);
		}
		return q.getResultList();
	}
	@Cacheable(SITIO_CACHE)
	public List<Sitio> getAll() {
		return em.createQuery("SELECT p FROM Sitio p order by id desc", Sitio.class).getResultList();
	}
	@CacheEvict(SITIO_CACHE)
	public void deleteById(Long id) throws Exception {
		Sitio sitio = em.find(Sitio.class, id);
		em.remove(sitio);
		evictCache();
	}
	@Cacheable(SITIO_CACHE)
	public List<Sitio> getAll(Sitio filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM sitio p where 1=1");
		if (filtro.getLugarObj()!=null && filtro.getLugarObj().getId() != null)
			sb.append(" and p.lugarObj_id =" + filtro.getLugarObj().getId());
		if (filtro.getTipo()!=null)
			sb.append(" and p.tipo_id =" + filtro.getTipo().getId());
		if (filtro.getNombre()!=null)
			sb.append(" and p.nombre like '%" + filtro.getNombre() +"%'");
		sb.append(" order by id desc");
		Query q = em.createNativeQuery(sb.toString(), Sitio.class);
		List<Sitio> resultado= q.getResultList();
        
		return resultado;
	}
	@Cacheable(SITIO_CACHE)
	public List<Sitio> getAll(Sitio filtro,String order,int page,int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM sitio p where 1=1");
		if (filtro.getLugarObj()!=null && filtro.getLugarObj().getId() != null)
			sb.append(" and p.lugarObj_id =" + filtro.getLugarObj().getId());
		if (filtro.getTipo()!=null)
			sb.append(" and p.tipo_id =" + filtro.getTipo().getId());
		if (filtro.getNombre()!=null)
			sb.append(" and p.nombre like '%" + filtro.getNombre() +"%'");		
		if(!GenericValidator.isBlankOrNull(order)){
			sb.append(" order by " + order);
		}else{
		    sb.append(" order by id desc");
		}		
		Query q = em.createNativeQuery(sb.toString(), Sitio.class);
		q.setFirstResult(page*pageSize);
		q.setMaxResults(pageSize);
		List<Sitio> resultado= q.getResultList();
        
		return resultado;
	}
	public void evictCache(){
		cacheManager.getCache(SITIO_CACHE).clear();
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	
}
