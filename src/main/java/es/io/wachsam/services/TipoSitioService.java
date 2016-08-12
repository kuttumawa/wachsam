package es.io.wachsam.services;

import java.util.Date;
import java.util.List;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.TipoSitioDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;


public class TipoSitioService {
	private TipoSitioDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	public TipoSitioDao getDao() {
		return dao;
	}
	public void setDao(TipoSitioDao dao) {
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
	public TipoSitio save(TipoSitio tipoSitio,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		TipoSitio tipoSitioOld=tipoSitio;
		if(tipoSitio.getId()!=null){
			operation=Acciones.UPDATE;
			tipoSitioOld=dao.getTipoSitio(tipoSitio.getId());
		}
		if(!securityService.hasAuth(usuario,TipoSitio.class, operation, tipoSitioOld))
		 throw new NoAutorizadoException();
		tipoSitio= dao.save(tipoSitio);
		operationLogDao.save(new OperationLog(tipoSitio.getClass().getSimpleName(),tipoSitio.getId(),operation.name(),usuario.getId(),new Date()));
		return tipoSitio;
		
	}
	
	public TipoSitio getTipoSitio(Long id,Usuario usuario) throws NoAutorizadoException{
			Acciones operation=Acciones.READ;
			TipoSitio tipoSitio=dao.getTipoSitio(id);
			if(!securityService.hasAuth(usuario,TipoSitio.class, operation, tipoSitio))
			 throw new NoAutorizadoException();
			return tipoSitio;
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		TipoSitio tipoSitio=dao.getTipoSitio(id);
		if(!securityService.hasAuth(usuario,TipoSitio.class, Acciones.DELETE, tipoSitio))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(tipoSitio.getClass().getSimpleName(),tipoSitio.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	public List<TipoSitio> getAll(){
		return dao.getAll();
	}
	

}
