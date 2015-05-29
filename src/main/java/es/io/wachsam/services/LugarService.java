package es.io.wachsam.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.DB;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class LugarService {
	private LugarDao dao;
	
	public LugarDao getDao() {
		return dao;
	}
	public void setDao(LugarDao dao) {
		this.dao = dao;
	}
	
	
	
	

}
