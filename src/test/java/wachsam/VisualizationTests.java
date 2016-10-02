package wachsam;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.LugarService;
import es.io.wachsam.services.TipoSitioService;

public class VisualizationTests {
	static ClassPathXmlApplicationContext context;
	LugarService lugarService=null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
	    context = new ClassPathXmlApplicationContext("applicationContext_test.xml");		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Usuario user=new Usuario();
		user.setId(11L);
		lugarService=(LugarService) context.getBean("lugarService");
		Lugar l=lugarService.getLugar(43L, user);
		System.out.println(lugarService.getAllLugaresConJerarquiaDe(l, new StringBuilder()).toString().
				replaceAll("\\},\\]", "}]").replaceAll("\\]\\},$", "]}"));
		
	}
	@Test
	public void test2() {
		StringBuilder sb=new StringBuilder("01,2345,");
		System.out.println(sb.substring(7,8));
		System.out.println(sb.lastIndexOf(","));
		System.out.println(sb.replace(sb.lastIndexOf(","),sb.lastIndexOf(",")+1,""));
		System.out.println(sb);
	}

}
