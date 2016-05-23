package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

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
	 *            [tag,objetoId,connectToId]
	 * @return
	 */
	public List<Data> getDatas(Long[] vector) {
		String sql = "SELECT p FROM Data p where tag=:tag and objetoId:objetoId and connectToId:connectToId";
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
		if (filtro.getObjetoId() != null)
			sb.append(" and p.objetoId =" + filtro.getObjetoId());	
		if (filtro.getConnectToId() != null)
			sb.append(" and p.connectToId =" + filtro.getConnectToId());		
		if (filtro.getObjetoTipo() != null)
			sb.append(" and p.objetoTipo =" + filtro.getObjetoTipo().ordinal());
		if (filtro.getObjetoConnected() != null)
			sb.append(" and p.objetoConnectedTipo =" + filtro.getObjetoConnected().ordinal());
		if (filtro.getTag() != null)
			sb.append(" and p.tag_id =" + filtro.getTag().getId());
		
		sb.append(" order by id desc");
		Query q = em.createNativeQuery(sb.toString(), Data.class);
		List<Data> resultado= q.getResultList();
        
		return resultado;
	}
	
}
