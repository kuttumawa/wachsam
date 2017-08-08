package es.io.wachsam.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
		riesgo.setFechapub(new Date());		
		if(riesgo.getId() == null){ 
			em.persist(riesgo);
		}
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
				"SELECT p FROM Riesgo p where p.lugar.id = :lugarid order by p.probabilidad desc", Riesgo.class);
		q.setParameter("lugarid",idpais);
		return q.getResultList();
	}
	public List<Riesgo> getRiesgosFromLugarConAscendientes(Long idpais) {
		/*
		 * ie: Para Argelia 
				 * SELECT
		    p.id as parent_id,
		    p.nombre as parent_id,
		    c1.id as child_id,
		    c1.nombre as child_name,
			c2.id as child_id,
		    c2.nombre as child_name,
		    c3.id as child_id,
		    c3.nombre as child_name
			FROM 
			    viajarseguro.lugar p
			LEFT JOIN viajarseguro.lugar c1
			    ON c1.id= p.padre1_id and c1.id != p.id
			LEFT JOIN viajarseguro.lugar c2
			    ON c2.id = c1.padre1_id and c2.id != c1.id
			LEFT JOIN viajarseguro.lugar c3
			    ON c3.id = c2.padre1_id and c3.id != c2.id
			WHERE
			    p.id=19
		 */
		
		Query q = em.createQuery(
				"SELECT p FROM Riesgo p where p.lugar.id = :lugarid order by p.value desc", Riesgo.class);
		q.setParameter("lugarid",idpais);
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

	public boolean existeYaRiesgo(Riesgo riesgo) {
		Query q = em.createQuery(
				"SELECT p FROM Riesgo p where p.peligro.id = :peligroid and p.lugar.id =:lugarId", Riesgo.class);
		q.setParameter("peligroid",riesgo.getPeligro().getId());
		q.setParameter("lugarId",riesgo.getLugar().getId());
		if (q.getResultList()==null || q.getResultList().isEmpty()) return false;
		else return true;
	}

	public List<Riesgo> searchRiesgos(String[] countrycodes, String[] countryIds, String[] peligroIds, String lang,
			String max, String index) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Riesgo> getRiesgosFromLugar(Long idpais, List<Long> peligroIds) {
		String q1="SELECT p FROM Riesgo p where p.lugar.id = :lugarid and p.peligro.id in (:peligroIds) order by p.probabilidad desc";
		String q2="SELECT p FROM Riesgo p where p.lugar.id = :lugarid  order by p.probabilidad desc";
		String queryStr="SELECT p FROM Riesgo p where p.lugar.id = :lugarid  order by p.probabilidad desc";
        if(peligroIds!=null && peligroIds.size()>0) queryStr=q1;
		Query q = em.createQuery(queryStr, Riesgo.class);
		q.setParameter("lugarid",idpais);
		if(peligroIds!=null && peligroIds.size()>0) q.setParameter("peligroIds",peligroIds);		
		return q.getResultList();
	}

	
	

	
	
	
	
}
