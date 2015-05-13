package wachsam;



import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.services.AlertService;

public class InceptionTest extends TestCase {
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext.xml");
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
	@Test
	public void testToJson() throws IOException{
		  assertEquals(true,true);
		  AlertService service=(AlertService) context.getBean("alertService");
	      AlertasDao dao = (AlertasDao) context.getBean("alertasDao");
	      List<Alert> alertas=service.unMarshallIt(new File("alert.csv"));
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
		
	}
}
