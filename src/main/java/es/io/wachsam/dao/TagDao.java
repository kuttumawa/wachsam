package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Tag;

@Transactional
public class TagDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Tag tag) {
		if (tag == null)
			return -1L;
		if(tag.getId() == null) em.persist(tag);
		else em.merge(tag);
		return tag.getId();
	}
	
	public Tag getTag(Long id){
		return em.find(Tag.class,id);
	}
	
	public List<Tag> getAll() {
		return em.createQuery("SELECT p FROM Tag p order by nombre desc", Tag.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Tag tag = em.find(Tag.class, id);
		em.remove(tag); 
	}
}