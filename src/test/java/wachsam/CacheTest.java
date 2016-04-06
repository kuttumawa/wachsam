package wachsam;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.repositories.PeligroRepository;

public class CacheTest extends TestCase {
	@Resource
	private PeligroRepository repository;
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private List<Sitio> sitios=new ArrayList<Sitio>();
	private DataDao dataDao;
	private TagDao tagDao;
	private SitioDao sitioDao;
	private AlertasDao alertDao;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		dataDao=(DataDao) context.getBean("dataDao");
		tagDao=(TagDao) context.getBean("tagDao");
		sitioDao=(SitioDao) context.getBean("sitioDao");
		alertDao=(AlertasDao) context.getBean("alertasDao");
		assertNotNull("No inicializado dataDao",dataDao);
		assertNotNull("No inicializado tagDao",tagDao);
	}

	protected void tearDown() throws Exception {
		for(Data data:datas){
			dataDao.deleteById(data.getId());
		}
		for(Tag tag:tags){
			tagDao.deleteById(tag.getId());
		}
		for(Sitio sitio:sitios){
			tagDao.deleteById(sitio.getId());
		}
		
	}

	@Test
	public void testECache() throws IOException{
	 Date t0,t1;
	  for(int i=1;i<10;i++){
		 t0=new Date();
		 alertDao.getAll();
		 t1=new Date();
		 System.out.println("ex1:" + (t1.getTime()-t0.getTime()));
	  }
	}
	
	
	
	private Sitio createNewSitio(String tagName){
		Sitio sitio=new Sitio();
		sitio.setNombre(tagName);
		sitio.setNombreEn("tagName_EN"+ new Random().nextInt(99999999));
		sitio.setLugarObj(null); 
		sitio.setTexto("texto");
		sitio.setTexto("textoEn");
		sitio.setTipo(TipoSitio.values()[0]);
		return sitio;
	}
	
	
}
