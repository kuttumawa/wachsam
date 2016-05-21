package es.io.wachsam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.Usuario;

@Transactional
public class UsuarioDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Usuario usuario) {
		if (usuario == null)
			return -1L;
		if(usuario.getId() == null) em.persist(usuario);
		else em.merge(usuario);
		return usuario.getId();
	}
	
	public Usuario getUsuario(Long id){
		return em.find(Usuario.class,id);
	}
	
	public List<Usuario> getAll() {
		return em.createQuery("SELECT p FROM Usuario p order by login asc", Usuario.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Usuario usuario = em.find(Usuario.class, id);
		em.remove(usuario); 
	}
	public Long savePermiso(Permiso permiso) {
		if (permiso == null)
			return -1L;
		if(permiso.getId() == null) em.persist(permiso);
		else em.merge(permiso);
		return permiso.getId();
	}
	
	public Permiso getPermiso(Long id){
		return em.find(Permiso.class,id);
	}
	
	public List<Permiso> getAllPermiso() {
		return em.createQuery("SELECT p FROM Permiso p order by id asc", Permiso.class)
				.getResultList();
	}

	public void deleteByIdPermiso(Long id) throws Exception {
		Permiso permiso = em.find(Permiso.class, id);
		em.remove(permiso); 
	}

	public Usuario getUsuario(String login, String password) {
		Query q = em.createQuery(
				"SELECT u FROM Usuario u where u.login = :login and u.password = :password", Usuario.class);
		q.setParameter("login",login);
		q.setParameter("password",password);
		List<?> r=q.getResultList();
		if(r.size()==1) return (Usuario) r.get(0);
		return null;
	}
}
