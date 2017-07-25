package wachsam;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.NodeAndLinks;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.repositories.PeligroRepository;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.LugarService;

public class LugarTest extends TestCase {
	@Resource

	private LugarDao lugarDao;
	private LugarService lugarService;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		
		lugarDao=(LugarDao) context.getBean("lugarDao");
		lugarService=(LugarService) context.getBean("lugarService");
		assertNotNull("No inicializado lugarDao",lugarDao);
		assertNotNull("No inicializado lugarDao",lugarService);
	}

	
	@Test
	public void testGetLugaresAscendente() throws IOException{
	 assertEquals(true,true);
	 Lugar lugar=lugarDao.getLugar(new Long(36));
	 List<Lugar> lugares=lugarDao.getAscendientes(lugar, null);
	 assertNotNull(lugares);
	System.out.println("res::> "+lugares.size());
	
	}
	
}
