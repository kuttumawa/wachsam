package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Airport;


@Transactional
public class AirportDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Airport airport) {
		if (airport == null)
			return -1L;
		if(airport.getId() == null) em.persist(airport);
		else em.merge(airport);
		return airport.getId();
	}
	
	public Airport getAirport(Long id){
		return em.find(Airport.class,id);
	}

	public List<Airport> getAll() {
		return em.createQuery("SELECT p FROM Airport p order by name", Airport.class)
				.getResultList();
	}

	
	
	public void deleteById(Long id) throws Exception {
		Airport ent = em.find(Airport.class, id);
		em.remove(ent); 
	}

	
	
	
}
