package wachsam;



import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.dao.RiesgoDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Mes;
import es.io.wachsam.model.NodeAndLinks;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.repositories.PeligroRepository;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.RiesgoService;

public class RiesgoTest extends TestCase {
	@Resource

	
	private RiesgoService riesgoService;
	private LugarDao lugarDao;
	private PeligroDao peligroDao;
	private RiesgoDao riesgoDao;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		
		
		riesgoService=(RiesgoService) context.getBean("riesgoService");
		lugarDao=(LugarDao) context.getBean("lugarDao");
		riesgoDao=(RiesgoDao) context.getBean("riesgoDao");
		peligroDao=(PeligroDao) context.getBean("peligroDao");
		assertNotNull("No inicializado lugarDao",riesgoService);
	}
    private void initData(){
    	//Argelia(36)   ->  Norte Africa(19)       -> Africa(3)       -> Universo(1)
    	//                  Malaria(269)[25%]        Malaria[50%]
    	//Atentado(17)
    	//                  Calor(27)[50%] 
    	//                  Dengue(48)[25%]          Dengue(48)[50%]
    	//                  Escorpion(234)
    	//                                                               Accidente Aéreo(1)
    	Lugar argelia = lugarDao.getLugar(36L);
    	
    	
    	//riesgoDao.save(new Riesgo());
    }
    private void deleteData(){
    	
    	
    }
    
    @Test
	public void testEnum() throws IOException{
	  System.out.println(Mes.values());
	  System.out.println(Mes.values()[0]);
	  System.out.println(Mes.values()[1]);
	  try{
		   System.out.println(Mes.valueOf("DiciembreX"));
			  
	  }catch(IllegalArgumentException e){
	     System.out.println("DiciembreX:: Error...");
	  }
	}
    @Test
   	public void testCalendar() throws IOException{
   	  Calendar hoy=new GregorianCalendar();
   	  Calendar pasado=new GregorianCalendar(2017,6,1);
   	  System.out.println(hoy.getTime());
   	  System.out.println(pasado.getTime());
   	  System.out.println(daysBetween(hoy.getTime(),pasado.getTime()));
   	}
    
    private int daysBetween(Date d1, Date d2){
        return (int)( (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
    }
	
	@Test
	public void testRiesgosFromLugar() throws IOException{
	 assertEquals(true,true);
	 List<Riesgo> riesgos=riesgoService.getAllRiesgoForLugarConAscendentes(36L);
	 assertNotNull(riesgos);
	 System.out.println("res::> "+riesgos.size());
	
	}


	public RiesgoService getRiesgoService() {
		return riesgoService;
	}


	public void setRiesgoService(RiesgoService riesgoService) {
		this.riesgoService = riesgoService;
	}
	
}
