package es.io.wachsam.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;

@Transactional
public class DataDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Data data) {
		if (data == null)
			return -1L;
		if (data.getId() == null)
			em.persist(data);
		else
			em.merge(data);
		return data.getId();
	}

	public Data getData(Long id) {
		return em.find(Data.class, id);
	}

	/**
	 * @param vector
	 *            [tag1,tag2,tag3,lugarId,subjectId,eventoId]
	 * @return
	 */
	public List<Data> getDatas(Long[] vector) {
		String sql = "SELECT p FROM Data p where tag1=:tag1 and tag2=:tag2 and tag3=:tag3 and lugarId=:lugarId "
				+ "and subjectId=:subjectId and eventoId=:eventoId";
		Query q = em.createQuery(sql, Data.class);
		for (int i = 0; i < vector.length; i++) {
			q.setParameter(i, vector[i]);
		}
		return q.getResultList();
	}

	public List<Data> getAll() {
		return em.createQuery("SELECT p FROM Data p order by id desc", Data.class).getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Data data = em.find(Data.class, id);
		em.remove(data);
	}

	public List<Data> getAll(Data filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM data p where 1=1");
		if (filtro.getLugarId() != null)
			sb.append(" and p.lugarId =" + filtro.getLugarId());
		else
			sb.append(" and p.lugarId is null");
		if (filtro.getSubjectId() != null)
			sb.append(" and p.subjectId =" + filtro.getSubjectId());
		else
			sb.append(" and p.subjectId is null");
		if (filtro.getEventoId() != null)
			sb.append(" and p.eventoId =" + filtro.getEventoId());
		else
			sb.append(" and p.eventoId is null");
		
		sb.append(" and ");
		
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag1_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag1_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag2_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag2_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag3_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag3_id is null)");
		sb.append(" or ");
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag2_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag2_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag1_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag1_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag3_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag3_id is null)");
		sb.append(" or ");
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag3_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag3_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag1_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag1_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag2_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag2_id is null)");
		sb.append(" or ");
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag3_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag3_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag2_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag2_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag1_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag1_id is null)");
		sb.append(" or ");
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag1_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag1_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag3_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag3_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag2_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag2_id is null)");
		sb.append(" or ");
		
		if (filtro.getTag1() != null)
			sb.append(" (p.tag2_id =" + filtro.getTag1().getId());
		else
			sb.append(" (p.tag2_id is null");
		if (filtro.getTag2() != null)
			sb.append(" and p.tag3_id =" + filtro.getTag2().getId());
		else
			sb.append(" and p.tag3_id is null");
		if (filtro.getTag3() != null)
			sb.append(" and p.tag1_id =" + filtro.getTag3().getId()+")");
		else
			sb.append(" and p.tag1_id is null)");
		
		

		sb.append(" order by id desc");
		System.out.println(">> " + sb.toString());
		Query q = em.createNativeQuery(sb.toString(), Data.class);
		List<Data> resultado= q.getResultList();
        
		return resultado;
	}
	public List<Data> getAllnoExtrict(Data filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM data p where 1=1");
		if (filtro.getLugarId() != null)
			sb.append(" and p.lugarId =" + filtro.getLugarId());
		
		if (filtro.getSubjectId() != null)
			sb.append(" and p.subjectId =" + filtro.getSubjectId());
		
		if (filtro.getEventoId() != null)
			sb.append(" and p.eventoId =" + filtro.getEventoId());
		
		if (filtro.getSitioId() != null)
			sb.append(" and p.sitioId =" + filtro.getSitioId());
		
		

		sb.append(" order by id desc");
		System.out.println(">> " + sb.toString());
		Query q = em.createNativeQuery(sb.toString(), Data.class);
		List<Data> resultado= q.getResultList();
        
		return resultado;
	}
}
