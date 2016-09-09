package es.io.wachsam.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

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
		Alert checkedAlert=alert;
		if(alert.getId()!=null){
			operation=Acciones.UPDATE;
			checkedAlert=dao.getAlert(alert.getId());
		}
		if(!securityService.hasAuth(usuario,Alert.class, operation, checkedAlert))
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
		String tipo=filter.containsKey("tipoId")?filter.get("tipoId"):null;
		String order=filter.containsKey("order")?filter.get("order"):null;
		Long lugarId=null;
		Long peligroId=null;
		Long id=null;
		try{
			lugarId=filter.containsKey("lugarId") && GenericValidator.isLong(filter.get("lugarId"))?Long.parseLong(filter.get("lugarId")):null;
			peligroId=filter.containsKey("peligroId") && GenericValidator.isLong(filter.get("peligroId")) ?Long.parseLong(filter.get("peligroId")):null;
			id=filter.containsKey("id")&& GenericValidator.isLong(filter.get("id"))?Long.parseLong(filter.get("id")):null;
		}catch(Exception e){
			
		}
		Date fechaDesde=null;
		if(filter.containsKey("fechaPubDesde")){
			try{
			    fechaDesde=new SimpleDateFormat("yyyy-MM-dd").parse(filter.get("fechaPubDesde"));
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
				
		List<Alert> alerts=dao.getAlertasMysql(id,texto,lugarId,peligroId,fechaDesde, tipo, order,caducadas,page,pageSize);
		
		return alerts;
	}
	public int getNumeroTotalSitios(Map<String,String> filter) {
		String texto=filter.containsKey("texto")?filter.get("texto"):null;
		String tipo=filter.containsKey("tipoId")?filter.get("tipoId"):null;
		String order=filter.containsKey("order")?filter.get("order"):null;
		Long lugarId=null;
		Long peligroId=null;
		Long id=null;
		try{
			lugarId=filter.containsKey("lugarId") && GenericValidator.isLong(filter.get("lugarId"))?Long.parseLong(filter.get("lugarId")):null;
			peligroId=filter.containsKey("peligroId") && GenericValidator.isLong(filter.get("peligroId")) ?Long.parseLong(filter.get("peligroId")):null;
			id=filter.containsKey("id")&& GenericValidator.isLong(filter.get("id"))?Long.parseLong(filter.get("id")):null;
     	}catch(Exception e){
			
		}
		Date fechaDesde=null;
		if(filter.containsKey("fechaPubDesde")){
			try{
			    fechaDesde=new SimpleDateFormat("yyyy-MM-dd").parse(filter.get("fechaPubDesde"));
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
				
		List<Alert> alerts=dao.getAlertasMysql(id,texto,lugarId,peligroId,fechaDesde, tipo, order,caducadas,-1,-1);
		
		return alerts.size();
	}
	
	
	
	
	

}
