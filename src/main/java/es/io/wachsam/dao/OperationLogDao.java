package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.OperationLog;

@Transactional
public class OperationLogDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(OperationLog operationLog) {
		if (operationLog == null)
			return -1L;
		if(operationLog.getId() == null){
			em.persist(operationLog);
		}
		else em.merge(operationLog);
		return operationLog.getId();
	}
	
	public OperationLog getOperationLog(Long id){
		return em.find(OperationLog.class,id);
	}
	
	public List<OperationLog> getAll() {
		return em.createQuery("SELECT p FROM OperationLog p order by id desc", OperationLog.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		OperationLog operationLog = em.find(OperationLog.class, id);
		em.remove(operationLog); 
	}
	
	public List<Object[]> accesosAgrupadosPorUsuarioDia(){
		
		List<Object[]> results = this.em.createNativeQuery("SELECT count(*),b.login, DATE_FORMAT(timestamp, '%e %b %Y') AS date_formatted from operationLog a,usuario b where usuarioId=b.id group by date_formatted,usuarioId").getResultList();
        return results;
		
	}
}
