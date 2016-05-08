package es.io.wachsam.services;

import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.AccionesSobreObjetosTipos;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class SecurityService {
	private UsuarioDao usuarioDao;

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public boolean hasAuth(Usuario user, Class<?> class1, AccionesSobreObjetosTipos accion) {
		Usuario usuario=usuarioDao.getUsuario(user.getId());
		for(Permiso permiso:usuario.getPermisos()){
			if(permiso.getObjeto().equalsIgnoreCase(class1.getName())){
				if(permiso.getAccion().equals(AccionesSobreObjetosTipos.ALL) || permiso.getAccion().equals(accion)) return true;
			}
		}
		return false;
	}

	public Usuario login(String login, String password) {
		Usuario user=usuarioDao.getUsuario(login,password);
		return user;
	}

	
	
	

}
