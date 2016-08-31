package es.io.wachsam.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;


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
		Sitio sitioOld=sitio;
		if(sitio.getId()!=null){
			operation=Acciones.UPDATE;
			sitioOld=dao.getSitio(sitio.getId());
		}
		if(!securityService.hasAuth(usuario,Sitio.class, operation, sitioOld))
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
	public List<Sitio> getAll() {
		return dao.getAll();
	}
	public List<Sitio> getSitios(Map<String, String> filter, int pageNum,
			int pageSize) {
		Sitio sitio=new Sitio();
		String order=filter.containsKey("order")?filter.get("order"):null;
		if(filter.containsKey("id") && GenericValidator.isLong(filter.get("id"))){
			sitio.setId(Long.parseLong(filter.get("id")));
		}
		if(filter.containsKey("lugarId") && GenericValidator.isLong(filter.get("lugarId"))){
			try{
				Lugar lugar=new Lugar();
				lugar.setId(Long.parseLong(filter.get("lugarId")));
			    sitio.setLugarObj(lugar);
			}catch(Exception e){
			
			}
		}
		if(filter.containsKey("tipoId") && GenericValidator.isLong(filter.get("tipoId"))){
			try{
				TipoSitio tipo=new TipoSitio();
				tipo.setId(Long.parseLong(filter.get("tipoId")));
				sitio.setTipo(tipo);
			}catch(Exception e){
			
			}
		}
		List<Sitio> sitios=dao.getAll(sitio,order,pageNum,pageSize);
		
		return sitios;
	}
	public int getNumeroTotalSitios(Map<String, String> filter) {
		Sitio sitio=new Sitio();
		String order=filter.containsKey("order")?filter.get("order"):null;
		if(filter.containsKey("id") && GenericValidator.isLong(filter.get("id"))){
			sitio.setId(Long.parseLong(filter.get("id")));
		}
		if(filter.containsKey("lugarId") && GenericValidator.isLong(filter.get("lugarId"))){
			try{
				Lugar lugar=new Lugar();
				lugar.setId(Long.parseLong(filter.get("lugarId")));
			    sitio.setLugarObj(lugar);
			}catch(Exception e){
			
			}
		}
		if(filter.containsKey("tipoId") && GenericValidator.isLong(filter.get("tipoId"))){
			try{
				TipoSitio tipo=new TipoSitio();
				tipo.setId(Long.parseLong(filter.get("tipoId")));
				sitio.setTipo(tipo);
			}catch(Exception e){
			
			}
		}
		List<Sitio> sitios=dao.getAll(sitio);		
		return sitios.size();	
	}
	
	

}
