package es.io.wachsam.services;

import java.util.Date;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.RecursoDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.Usuario;


public class RecursoService {
	private RecursoDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	public RecursoDao getDao() {
		return dao;
	}
	public void setDao(RecursoDao dao) {
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
	public Recurso save(Recurso recurso,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		Recurso riegoOld=recurso;
		if(recurso.getId()!=null){
			operation=Acciones.UPDATE;
			riegoOld=dao.getRecurso(recurso.getId());
		}
		if(!securityService.hasAuth(usuario,Recurso.class, operation, riegoOld))
		 throw new NoAutorizadoException();
		recurso= dao.save(recurso);
		operationLogDao.save(new OperationLog(recurso.getClass().getSimpleName(),recurso.getId(),operation.name(),usuario.getId(),new Date()));
		return recurso;
		
	}
	
	public Recurso getRecurso(Long id,Usuario usuario) throws NoAutorizadoException{
			Acciones operation=Acciones.READ;
			Recurso recurso=dao.getRecurso(id);
			if(!securityService.hasAuth(usuario,Recurso.class, operation, recurso))
			 throw new NoAutorizadoException();
			return recurso;
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Recurso recurso=dao.getRecurso(id);
		if(!securityService.hasAuth(usuario,Recurso.class, Acciones.DELETE, recurso))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(recurso.getClass().getSimpleName(),recurso.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	
	public boolean existeYaRecurso(Recurso recurso) {
		return dao.existeYaRecurso(recurso);
		
	}
	

}
