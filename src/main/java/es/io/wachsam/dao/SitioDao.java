package es.io.wachsam.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Sitio;

@Transactional
public class SitioDao {

	@PersistenceContext
	private EntityManager em;
	@CachePut("ioCacheSitio")
	public Long save(Sitio sitio) {		
		if (sitio == null)
			return -1L;
		if (sitio.getId() == null)
			em.persist(sitio);
		else
			em.merge(sitio);
		return sitio.getId();
	}

	public Sitio getSitio(Long id) {
		return em.find(Sitio.class, id);
	}

	/**
	 * @param vector
	 *            [tag1,tag2,tag3,lugarId,subjectId,eventoId]
	 * @return
	 */
	@Cacheable("ioCacheSitio")
	public List<Sitio> getSitios(Long[] vector) {
		String sql = "SELECT p FROM Sitio p where tag1=:tag1 and tag2=:tag2 and tag3=:tag3 and lugarId=:lugarId "
				+ "and subjectId=:subjectId and eventoId=:eventoId";
		Query q = em.createQuery(sql, Sitio.class);
		for (int i = 0; i < vector.length; i++) {
			q.setParameter(i, vector[i]);
		}
		return q.getResultList();
	}
	@Cacheable("ioCacheSitio")
	public List<Sitio> getAll() {
		return em.createQuery("SELECT p FROM Sitio p order by id desc", Sitio.class).getResultList();
	}
	@CacheEvict("ioCacheSitio")
	public void deleteById(Long id) throws Exception {
		Sitio sitio = em.find(Sitio.class, id);
		em.remove(sitio);
	}
	@Cacheable("ioCacheSitio")
	public List<Sitio> getAll(Sitio filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM Sitio p where 1=1");
		if (filtro.getLugarObj()!=null && filtro.getLugarObj().getId() != null)
			sb.append(" and p.lugarId =" + filtro.getLugarObj().getId());
		if (filtro.getTipo()!=null)
			sb.append(" and p.tipo =" + filtro.getTipo());
		
		sb.append(" order by id desc");
		System.out.println(">> " + sb.toString());
		Query q = em.createNativeQuery(sb.toString(), Sitio.class);
		List<Sitio> resultado= q.getResultList();
        
		return resultado;
	}
}
