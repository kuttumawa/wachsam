package wachsam;



import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.CategoriaPeligro;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.services.AlertService;

public class InceptionTest extends TestCase {
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*@Test
	public void testIsDBThere() throws IOException{
	 assertEquals(true,true);
	 AlertService service=(AlertService) context.getBean("alertService");
     AlertasDao dao = (AlertasDao) context.getBean("alertasDao");
     List<Alert> alertas=service.unMarshallIt(new File("alert.csv"));
     for(Alert a: alertas){
    	 dao.save(a);
     }
      List<Alert> alerts = dao.getAlertas(null,"PAKISTAN",null,"seve");
      for (Alert alert : alerts) {
          System.out.println(alert);
      }
      context.close();
	}
	
	@Test
	public void testParseFileAlerts() throws IOException{
	 AlertService service=(AlertService) context.getBean("alertService");
	 List<Alert> alerts=service.unMarshallIt(new File("alert.csv"));
	 assertTrue(alerts.size()>0);
		
	}*/
	/*@Test
	public void testToJson() throws IOException{
		  assertEquals(true,true);
		  AlertService service=(AlertService) context.getBean("alertService");
	      AlertasDao dao = (AlertasDao) context.getBean("alertasDao");
	      List<Alert> alertas=service.unMarshallIt(new File("src/main/resources/alert.csv"));
	      for(Alert a: alertas){
	    	 dao.save(a);
	      }
	      List<Alert> alerts = dao.getAlertas(null,"PAKISTAN",null,"seve", null);
	      final Gson gson=new Gson();
	      final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	      System.out.println("----------------------------------------------------");
	      System.out.println(prettyGson.toJson(alerts));
	      System.out.println("----------------------------------------------------");
	      context.close();
		
	}*/
	/*@Test
	public void testLugarAlerta(){
		 LugarDao dao=(LugarDao) context.getBean("lugarDao"); 
		 AlertasDao daoAlert=(AlertasDao) context.getBean("alertasDao");
		 PeligroDao daoPeligro=(PeligroDao) context.getBean("peligroDao");
		 assertNotNull(dao);
		 assertNotNull(daoAlert);
		 Lugar universo=new Lugar(0L,"Universo","Universe",null,null,null,"0","0",0);
		 Lugar europa=new Lugar(1L,"Europa","Europe",universo,null,null,"0","0",0);
		 dao.save(universo);
		 Long id=dao.save(europa);
		 Lugar lugar=dao.getLugar(id);
		 assertNotNull(lugar);
		 
		 Peligro peligro=new Peligro(1L,"Colera","Cholera",CategoriaPeligro.Enfermedad,3);
		 id=daoPeligro.save(peligro);
		 peligro=null;
		 peligro =daoPeligro.getPeligro(id);
		 assertNotNull(peligro);
		 
		 Alert _alert=new Alert(0L,"Tiburón","severa","xxxxxx",null,null,null,"xxxxxxxxxxccccccccccx","xxxxxxxxxsssss",new Date(),lugar,peligro);
		 Long idAlert=daoAlert.save(_alert);
		 assertNotNull(idAlert);
		 Alert alert=daoAlert.getAlert(idAlert);
		 assertNotNull(alert);
		 System.out.println(alert);
		 final Gson gson=new Gson();
	      final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	      System.out.println("----------------------------------------------------");
	      System.out.println(prettyGson.toJson(alert));
	      System.out.println("----------------------------------------------------");
	}
	*/
	
	@Test
	public void testComboLugares() throws IOException{
		  assertEquals(true,true);
		  LugarDao dao = (LugarDao) context.getBean("lugarDao");
		  
		  List<Lugar> lugares =dao.getAll();
	      
	      final Gson gson=new Gson();
	      final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	      System.out.println("----------------------------------------------------");
	      System.out.println(prettyGson.toJson(lugares));
	      System.out.println("----------------------------------------------------");
	      context.close();
		
	}
	
	
}
