package es.io.wachsam.services;

import java.util.Date;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Airport;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.PermisosAvanzados;
import es.io.wachsam.model.PermisosGod;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class SecurityService {
	private UsuarioDao usuarioDao;
    private OperationLogDao operationLogDao;
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public boolean hasAuth(Usuario user, Class<?> class1, Acciones accion,Object object) {
		Usuario usuario=usuarioDao.getUsuario(user.getId());
		if(object==null){
			try {
				object=class1.newInstance();
			} catch (InstantiationException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		boolean resultado=false;
		if(class1.getName().equalsIgnoreCase(Alert.class.getName())){
			resultado=((Alert)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Lugar.class.getName())){
			resultado=((Lugar)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Peligro.class.getName())){
			resultado=((Peligro)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Sitio.class.getName())){
			resultado=((Sitio)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Airport.class.getName())){
			resultado=((Airport)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Factor.class.getName())){
			resultado=((Factor)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Data.class.getName())){
			resultado=((Data)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Tag.class.getName())){
			resultado=((Tag)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(PermisosAvanzados.class.getName())){
			resultado=((PermisosAvanzados)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(PermisosGod.class.getName())){
			resultado=((PermisosGod)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Riesgo.class.getName())){
			resultado=((Riesgo)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(TipoSitio.class.getName())){
			resultado=((TipoSitio)object).hasPermisos(usuario, accion);
		}else if(class1.getName().equalsIgnoreCase(Recurso.class.getName())){
			resultado=((Recurso)object).hasPermisos(usuario, accion);
		}
		
		return resultado;
	}
	
	
	public Usuario login(String login, String password) {
		Usuario usuario=usuarioDao.getUsuario(login,password);
		if(usuario!=null) 
			operationLogDao.save(new OperationLog("Login",null,Acciones.LOGIN.name(),usuario.getId(),new Date()));
		return usuario;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	
	
	

}
