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

	public boolean hasAuth(Usuario user, Class<?> class1, AccionesSobreObjetosTipos accion,Object object) {
		Usuario usuario=usuarioDao.getUsuario(user.getId());
		boolean resultado=false;
		if(class1.getName().equalsIgnoreCase(Alert.class.getName())){
			resultado=((Alert)object).hasPermisos(usuario, accion);
		}
		return resultado;
	}
	

	public Usuario login(String login, String password) {
		Usuario user=usuarioDao.getUsuario(login,password);
		return user;
	}

	
	
	

}
