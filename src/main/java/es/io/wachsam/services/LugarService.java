package es.io.wachsam.services;

import java.util.Date;
import java.util.List;

import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class LugarService {
	private LugarDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	public LugarDao getDao() {
		return dao;
	}
	public void setDao(LugarDao dao) {
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
	public Lugar save(Lugar lugar,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		Lugar lugarOld=lugar;
		if(lugar.getId()!=null){
			operation=Acciones.UPDATE;
			lugar=dao.getLugar(lugar.getId());
		}
		if(!securityService.hasAuth(usuario,Lugar.class, operation, lugarOld))
		 throw new NoAutorizadoException();
		lugar= dao.save(lugar);
		operationLogDao.save(new OperationLog(lugar.getClass().getSimpleName(),lugar.getId(),operation.name(),usuario.getId(),new Date()));
		return lugar;
		
	}
	
	public Lugar getLugar(Long id,Usuario usuario){
			return dao.getLugar(id);
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Lugar lugar=dao.getLugar(id);
		if(!securityService.hasAuth(usuario,Lugar.class, Acciones.DELETE, lugar))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(lugar.getClass().getSimpleName(),lugar.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	public List<Lugar> getAll() {
		return dao.getAll();
	}
	public List<Lugar> getLugarFromTexto(String pais){
		return dao.getLugarFromTexto(pais);
	}
	public List<Long> getLugarFromISO_3166_1_alpha2(String code) {
		return dao.getLugarFromISO_3166_1_alpha2(code);
	}
	public List<Long> getLugarFromISO_3166_1_alpha3(String code) {
		return dao.getLugarFromISO_3166_1_alpha3(code);
	}
	public List<Long> getLugarFromISO_3166_1_num(String code) {
		return dao.getLugarFromISO_3166_1_num(code);
	}
	
	

}
