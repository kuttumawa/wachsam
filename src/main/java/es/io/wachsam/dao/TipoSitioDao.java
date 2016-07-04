package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.TipoSitio;


@Transactional
public class TipoSitioDao {

	@PersistenceContext
	private EntityManager em;

	public TipoSitio save(TipoSitio tipoSitio) {
		if (tipoSitio == null)
			return null;
		if(tipoSitio.getId() == null) em.persist(tipoSitio);
		else em.merge(tipoSitio);
		return tipoSitio;
	}
	
	public TipoSitio getTipoSitio(Long id){
		return em.find(TipoSitio.class,id);
	}

	public List<TipoSitio> getAll() {
		return em.createQuery("SELECT p FROM TipoSitio p order by nombre", TipoSitio.class)
				.getResultList();
	}

	
	
	public void deleteById(Long id) throws Exception {
		TipoSitio ent = em.find(TipoSitio.class, id);
		em.remove(ent); 
	}

	
	
	
}
