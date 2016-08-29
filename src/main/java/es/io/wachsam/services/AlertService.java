package es.io.wachsam.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
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
		Acciones operation=Acciones.CREATE;
		if(alert.getId()!=null) operation=Acciones.UPDATE;
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
		if(!securityService.hasAuth(usuario,Alert.class, Acciones.DELETE, alert))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(alert.getClass().getSimpleName(),alert.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	public List<Alert> getAll(){
		return dao.getAll();
	}
	public List<Alert> getAlertasMysql(Map<String,String> filter, int page,int pageSize) {
		String texto=filter.containsKey("texto")?filter.get("texto"):null;
		String tipo=filter.containsKey("tipo")?filter.get("tipo"):null;
		String order=filter.containsKey("order")?filter.get("order"):null;
		Long lugarId=null;
		Long peligroId=null;
		try{
			lugarId=filter.containsKey("lugarId")?Long.parseLong(filter.get("lugarId")):null;
			peligroId=filter.containsKey("peligroId")?Long.parseLong(filter.get("peligroId")):null;
		}catch(Exception e){
			
		}
		Date fechaDesde=null;
		if(filter.containsKey("fechaDesde")){
			try{
			    fechaDesde=new SimpleDateFormat("yyyy-MM-dd").parse(filter.get("fechaDesde"));
			}catch(Exception e){
					   //void
		    }
	    }
		Boolean caducadas=null;
		if(filter.containsKey("caducadas")){
			try{
			    caducadas=Boolean.parseBoolean(filter.get("caducadas"));
			}catch(Exception e){
					   //void
		    }
	    }
				
		List<Alert> alerts=dao.getAlertasMysql(texto,lugarId,peligroId,fechaDesde, tipo, order,caducadas,page,pageSize);
		
		return alerts;
	}
	
	
	
	
	

}
