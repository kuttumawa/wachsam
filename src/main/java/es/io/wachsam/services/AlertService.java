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
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.DB;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class AlertService {
	private AlertasDao dao;
	
	public AlertasDao getDao() {
		return dao;
	}
	public void setDao(AlertasDao dao) {
		this.dao = dao;
	}
	public List<Alert> unMarshallIt(File file) throws IOException{
		List<Alert> alertas=new ArrayList<Alert>();
		  StringBuilder contents = new StringBuilder();
		  BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file),"ISO-8859-1"));
	      try {
	        String line = null;
	          while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	          String[] temp=line.split(";");
	          Alert _alert=Alert.createAlert(temp);
	          if(_alert!=null)alertas.add(_alert);
	        }
	      }
	      finally {
	        input.close();
	      }
	      return alertas;
	}
	public List<Alert> unMarshallIt(InputStream ie) throws IOException{
		List<Alert> alertas=new ArrayList<Alert>();
		  StringBuilder contents = new StringBuilder();
		  BufferedReader input = new BufferedReader(new InputStreamReader(ie,"ISO-8859-1"));
	      try {
	        String line = null;
	          while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	          String[] temp=line.split(";");
	          Alert _alert=Alert.createAlert(temp);
	          if(_alert!=null)alertas.add(_alert);
	        }
	      }
	      finally {
	        input.close();
	      }
	      return alertas;
	}
	
	public void startDB() throws IOException{
		URL url = this.getClass().getResource("/alert.csv");
		File f=new File(url.getPath());
		DB db=dao.getDB(1L);
		if(db==null || !db.getSize().equals(f.length())) {
			dao.save(new DB(1L,f.length()));
		}else{
			return;
		}
		InputStream is=getClass().getClassLoader().getResourceAsStream("/alert.csv");
		List<Alert> alertas=unMarshallIt(is);
		System.out.println("Cargados----> " + alertas.size());
	    for(Alert a: alertas){
	    	 
	    	System.out.println(a);
	    	dao.save(a);
	    }
	}
	
	
	

}
