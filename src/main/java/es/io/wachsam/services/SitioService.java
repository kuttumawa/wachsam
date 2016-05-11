package es.io.wachsam.services;

import java.util.Date;

import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class SitioService {
	private SitioDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	public SitioDao getDao() {
		return dao;
	}
	public void setDao(SitioDao dao) {
		this.dao = dao;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
	public Sitio save(Sitio sitio,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		if(sitio.getId()!=null) operation=Acciones.UPDATE;
		if(!securityService.hasAuth(usuario,Sitio.class, operation, sitio))
		 throw new NoAutorizadoException();
		sitio= dao.save(sitio);
		operationLogDao.save(new OperationLog(sitio.getClass().getSimpleName(),sitio.getId(),operation.name(),usuario.getId(),new Date()));
		return sitio;
		
	}
	
	public Sitio getSitio(Long id,Usuario usuario){
			return dao.getSitio(id);
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Sitio sitio=dao.getSitio(id);
		if(!securityService.hasAuth(usuario,Sitio.class, Acciones.DELETE, sitio))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(sitio.getClass().getSimpleName(),sitio.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	

}
