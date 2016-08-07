package es.io.wachsam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.ObjetoSistemaIF;

@Transactional
public class FileUploadDao {

	@PersistenceContext
	private EntityManager em;

	public Object insert(Object o) {
		em.persist(o);
		return o;
	}

	public Object update(Object o) {
		em.merge(o);
		return o;
	}

	public ObjetoSistemaIF getObject(ObjetoSistemaIF os) {
		ObjetoSistemaIF oss= em.find(os.getClass(), os.getId());
		return oss;
	}
	

}
