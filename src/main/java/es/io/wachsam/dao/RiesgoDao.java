package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Riesgo;


@Transactional
public class RiesgoDao {
	

	@PersistenceContext
	private EntityManager em;
	

	public Riesgo save(Riesgo riesgo) {
		if (riesgo == null)
			return null;
		if(riesgo.getId() == null) em.persist(riesgo);
		else em.merge(riesgo);
	
		return riesgo;
	}
	
	public Riesgo getRiesgo(Long id){
		return em.find(Riesgo.class,id);
	}

	public List<Riesgo> getAll() {
		return em.createQuery("SELECT p FROM Riesgo p order by p.lugar.nivel", Riesgo.class)
				.getResultList();
	}

	public List<Riesgo> getRiesgosFromLugar(Long idpais) {
		Query q = em.createQuery(
				"SELECT p FROM Riesgo p where p.lugar.id = :lugarid", Riesgo.class);
		q.setParameter("lugar",idpais);
		return q.getResultList();
	}

	public List<Riesgo> getRiesgosFromPeligro(Long peligroid) {
		Query q = em.createQuery(
				"SELECT p FROM Riesgo p where p.peligro.id = :peligroid", Riesgo.class);
		q.setParameter("peligroid",peligroid);
		return q.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Riesgo ent = em.find(Riesgo.class, id);
		em.remove(ent); 
		
	}

	
	

	
	
	
	
}
