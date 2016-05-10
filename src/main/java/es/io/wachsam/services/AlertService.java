package es.io.wachsam.services;

import java.util.Date;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.AccionesSobreObjetosTipos;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class AlertService {
	private AlertasDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	
	public AlertasDao getDao() {
		return dao;
	}
	public void setDao(AlertasDao dao) {
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
	public Alert save(Alert alert,Usuario usuario) throws NoAutorizadoException{
		AccionesSobreObjetosTipos operation=AccionesSobreObjetosTipos.CREATE;
		if(alert.getId()!=null) operation=AccionesSobreObjetosTipos.UPDATE;
		if(!securityService.hasAuth(usuario,Alert.class, operation, alert))
		 throw new NoAutorizadoException();
		alert= dao.save(alert);
		operationLogDao.save(new OperationLog(alert.getClass().getSimpleName(),alert.getId(),operation.name(),usuario.getId(),new Date()));
		return alert;
		
	}
	
	public Alert getAlert(Long id,Usuario usuario){
			return dao.getAlert(id);
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Alert alert=dao.getAlert(id);
		if(!securityService.hasAuth(usuario,Alert.class, AccionesSobreObjetosTipos.DELETE, alert))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(alert.getClass().getSimpleName(),alert.getId(),AccionesSobreObjetosTipos.DELETE.name(),usuario.getId(),new Date()));
	}
	
	
	
	

}
