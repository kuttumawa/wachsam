package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Data;
import es.io.wachsam.model.Factor;

@Transactional
public class DataDao {
   
	@PersistenceContext
	private EntityManager em;

	public Long save(Data data) {
		if (data == null)
			return -1L;
		if(data.getId() == null) em.persist(data);
		else em.merge(data);
		return data.getId();
	}
	
	public Data getTag(Long id){
		return em.find(Data.class,id);
	}
	
	public Data getData(Long id){
		return em.find(Data.class,id);
	}
	public List<Data> getAll() {
		return em.createQuery("SELECT p FROM Data p order by id desc", Data.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Data data = em.find(Data.class, id);
		em.remove(data); 
	}

	public List<Data> getAll(Data filtro) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT p.* FROM Data p where 1=1");
		if(filtro.getLugarId()!=null) sb.append(" and p.lugarId =" + filtro.getLugarId());
		if(filtro.getSubjectId()!=null) sb.append(" and p.subjectId =" + filtro.getSubjectId());
		if(filtro.getEventoId()!=null) sb.append(" and p.eventoId =" + filtro.getEventoId());
		//if(filtro.tagsIdsToList()!=null) sb.append(" and (tags1.id in (:tags) or tags2.id in (:tags) or tags3.id in (:tags))");
		if(filtro.tagsIdsToList()!=null) sb.append(" and (tag1_id in ("+ filtro.tagsIdsToString() +") or tag2_id in ("+ filtro.tagsIdsToString() +") or tag3_id in ("+ filtro.tagsIdsToString() +")  )");
		
		sb.append(" order by id desc");
		System.out.println(">> "+sb.toString());
		Query q=em.createNativeQuery(sb.toString(),Data.class);
		
		return q.getResultList();
	}
}
