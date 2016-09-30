package es.io.wachsam.services;

import java.util.Date;
import java.util.List;

import es.io.wachsam.dao.MitigacionDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Mitigacion;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;

/**
 *
 *
 */
public class MitigacionService {
	private MitigacionDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;

	public MitigacionDao getDao() {
		return dao;
	}

	public void setDao(MitigacionDao dao) {
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

	public Mitigacion save(Mitigacion mitigacion, Usuario usuario)
			throws NoAutorizadoException {
		Acciones operation = Acciones.CREATE;
		Mitigacion mitigacionOld = mitigacion;
		if (mitigacion.getId() != null) {
			operation = Acciones.UPDATE;
			mitigacionOld = dao.getMitigacion(mitigacion.getId());
		}
		if (!securityService.hasAuth(usuario, Mitigacion.class, operation,
				mitigacionOld))
			throw new NoAutorizadoException();
		mitigacion = dao.save(mitigacion);
		operationLogDao.save(new OperationLog(mitigacion.getClass()
				.getSimpleName(), mitigacion.getId(), operation.name(), usuario
				.getId(), new Date()));
		return mitigacion;

	}

	public Mitigacion getMitigacion(Long id, Usuario usuario)
			throws NoAutorizadoException {
		Acciones operation = Acciones.READ;
		Mitigacion mitigacion = dao.getMitigacion(id);
		if (!securityService.hasAuth(usuario, Mitigacion.class, operation,
				mitigacion))
			throw new NoAutorizadoException();
		return mitigacion;
	}

	public void deleteById(Long id, Usuario usuario) throws Throwable {
		Mitigacion mitigacion = dao.getMitigacion(id);
		if (!securityService.hasAuth(usuario, Mitigacion.class,
				Acciones.DELETE, mitigacion))
			throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(mitigacion.getClass()
				.getSimpleName(), mitigacion.getId(), Acciones.DELETE.name(),
				usuario.getId(), new Date()));
	}

	public List<Mitigacion> getAll() {
		return dao.getAll();
	}

	public List<Mitigacion> getAllMitigacionForPeligro(Long idPeligro) {
		return dao.getMitigacionesFromPeligro(idPeligro);
	}

	public boolean existeYaMitigacion(Mitigacion mitigacion) {
		return dao.existeYaMitigacion(mitigacion);

	}

}
