package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.validator.GenericValidator;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.Sitio;


@Transactional
public class RecursoDao {
	

	@PersistenceContext
	private EntityManager em;
	

	public Recurso save(Recurso recurso) {
		if (recurso == null)
			return null;
		if(recurso.getId() == null) em.persist(recurso);
		else em.merge(recurso);
	
		return recurso;
	}
	
	public Recurso getRecurso(Long id){
		return em.find(Recurso.class,id);
	}

	

	public void deleteById(Long id) throws Exception {
		Recurso ent = em.find(Recurso.class, id);
		em.remove(ent); 
		
	}

	public boolean existeYaRecurso(Recurso recurso) {
		Query q = em.createQuery(
				"SELECT p FROM Recurso p where p.peligro.id = :peligroid and p.lugar.id =:lugarId", Recurso.class);
		if (q.getResultList()==null || q.getResultList().isEmpty()) return false;
		else return true;
	}

	public List<Recurso> getAll(Recurso recurso, String order, int pageNum,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM Recurso p LEFT JOIN Data d ON p.id=d.id where 1=1");
		if (recurso.getNombre()!=null)
			sb.append(" and p.nombre like '%" + recurso.getNombre() +"%'");	
		if (recurso.getDescripcion()!=null)
			sb.append(" and p.descripcion like '%" + recurso.getDescripcion() +"%'");
		if(!GenericValidator.isBlankOrNull(order)){
			sb.append(" order by " + order);
		}else{
		    sb.append(" order by id desc");
		}		
		Query q = em.createNativeQuery(sb.toString(), Recurso.class);
		q.setFirstResult(pageNum*pageSize);
		q.setMaxResults(pageSize);
		List<Recurso> resultado= q.getResultList();        
		return resultado;
	}
	public List<Recurso> getAll(Recurso recurso) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.* FROM Recurso p LEFT JOIN Data d ON p.id=d.id where 1=1");
		if (recurso.getNombre()!=null)
			sb.append(" and p.nombre like '%" + recurso.getNombre() +"%'");	
		if (recurso.getDescripcion()!=null)
			sb.append(" and p.descripcion like '%" + recurso.getDescripcion() +"%'");
			
		Query q = em.createNativeQuery(sb.toString(), Recurso.class);
		List<Recurso> resultado= q.getResultList();        
		return resultado;
	}

	
	

	
	
	
	
}
