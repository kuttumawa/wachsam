package es.io.wachsam.services;

import java.util.Date;
import java.util.List;

import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class PeligroService {
	private PeligroDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	public PeligroDao getDao() {
		return dao;
	}
	public void setDao(PeligroDao dao) {
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
	public Peligro save(Peligro peligro,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		Peligro peligroOld=peligro;
		if(peligro.getId()!=null) {
			operation=Acciones.UPDATE;
			peligroOld=dao.getPeligro(peligro.getId());
		}
		if(!securityService.hasAuth(usuario,Peligro.class, operation, peligroOld))
		 throw new NoAutorizadoException();
		peligro= dao.save(peligro);
		operationLogDao.save(new OperationLog(peligro.getClass().getSimpleName(),peligro.getId(),operation.name(),usuario.getId(),new Date()));
		return peligro;
		
	}
	
	public Peligro getPeligro(Long id,Usuario usuario){
			return dao.getPeligro(id);
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Peligro peligro=dao.getPeligro(id);
		if(!securityService.hasAuth(usuario,Peligro.class, Acciones.DELETE, peligro))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(peligro.getClass().getSimpleName(),peligro.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	public List<Peligro> getAll(){
		return dao.getAll();
	}
	public List<Peligro> getPeligroFromTexto(String texto) {
		return dao.getPeligroFromTexto(texto);
	}

}
