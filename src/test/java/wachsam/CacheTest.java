package wachsam;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;
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
	private LugarDao lugarDao;
	private PeligroDao peligroDao;
	private CacheManager cacheManager;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		dataDao=(DataDao) context.getBean("dataDao");
		tagDao=(TagDao) context.getBean("tagDao");
		lugarDao=(LugarDao) context.getBean("lugarDao");
		sitioDao=(SitioDao) context.getBean("sitioDao");
		alertDao=(AlertasDao) context.getBean("alertasDao");
		peligroDao=(PeligroDao) context.getBean("peligroDao");
		cacheManager=(CacheManager) context.getBean("cacheManager");
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
			sitioDao.deleteById(sitio.getId());
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
	
	@Test
	public void testECacheSitio() throws Exception{
		int num0=sitioDao.getAll().size();
		getTotalEhCacheSize();
	    System.out.println("sitio size before insert: " + num0);
		Sitio sitio = sitioDao.save(createNewSitio("NUEVO_SITIO"));		
		int num1=sitioDao.getAll().size();
	    System.out.println("sitio size after insert: " + num1);
	    assertEquals(num0+1,num1);
	    sitioDao.deleteById(sitio.getId());
	    int num2=sitioDao.getAll().size();
	    assertEquals(num0,num2);
	    
	}
	@Test
	public void testECacheAlert() throws Exception{
		int num0=alertDao.getAll().size();
		getTotalEhCacheSize();
	    System.out.println("alert size before insert: " + num0);
		Alert alert = alertDao.save(createNewAlert("NUEVO_Alert"));		
		int num1=alertDao.getAll().size();
	    System.out.println("alert size after insert: " + num1);
	    assertEquals(num0+1,num1);
	    alertDao.deleteById(alert.getId());
	    int num2=alertDao.getAll().size();
	    assertEquals(num0,num2);
	    
	}
	@Test
	public void testECacheLugar() throws Exception{
		int num0=lugarDao.getAll().size();
		getTotalEhCacheSize();
	    System.out.println("lugar size before insert: " + num0);
		Lugar lugar = lugarDao.save(createNewLugar("NUEVO_Lugar"));		
		int num1=lugarDao.getAll().size();
	    System.out.println("lugar size after insert: " + num1);
	    assertEquals(num0+1,num1);
	    lugarDao.deleteById(lugar.getId());
	    int num2=lugarDao.getAll().size();
	    assertEquals(num0,num2);
	    
	}
	@Test
	public void testECachePeligro() throws Exception{
		int num0=peligroDao.getAll().size();
		getTotalEhCacheSize();
	    System.out.println("peligro size before insert: " + num0);
		Peligro peligro = peligroDao.save(createNewPeligro("NUEVO_Peligro"));		
		int num1=peligroDao.getAll().size();
	    System.out.println("peligro size after insert: " + num1);
	    assertEquals(num0+1,num1);
	    peligroDao.deleteById(peligro.getId());
	    int num2=peligroDao.getAll().size();
	    assertEquals(num0,num2);
	    
	}
	
	  public long getTotalEhCacheSize() {
		    long totalSize = 0l;
		    for (String cacheName : cacheManager.getCacheNames()) {
		      Cache cache = cacheManager.getCache(cacheName);
		      Object nativeCache = cache.getNativeCache();
		      if (nativeCache instanceof net.sf.ehcache.Ehcache) {
		        net.sf.ehcache.Ehcache ehCache = (net.sf.ehcache.Ehcache) nativeCache;
		        System.out.println(nativeCache);
		        System.out.println("Disk size: " +((net.sf.ehcache.Ehcache) nativeCache).getStatistics().getLocalDiskSizeInBytes());
		        System.out.println("Heap size: " +((net.sf.ehcache.Ehcache) nativeCache).getStatistics().getLocalHeapSizeInBytes());
		      }
		    }
		    return totalSize;
		  }
	
	
	private Sitio createNewSitio(String name){
		Sitio sitio=new Sitio();
		sitio.setNombre(name);
		sitio.setNombreEn("tagName_EN"+ new Random().nextInt(99999999));
		sitio.setLugarObj(null); 
		sitio.setTexto("texto");
		sitio.setTexto("textoEn");
		sitio.setTipo(new TipoSitio(0L,null,null));
		return sitio;
	}
	private Lugar createNewLugar(String name){
		Lugar lugar=new Lugar();
		lugar.setNombre(name+ new Random().nextInt(99999999));
		lugar.setNombreEn(name+ new Random().nextInt(99999999));
		lugar.setLatitud("latitud");
		lugar.setLongitud("");
		lugar.setPadre1(null);
		return lugar;
	}
	private Peligro createNewPeligro(String name){
		Peligro peligro=new Peligro();
		peligro.setNombre(name+ new Random().nextInt(99999999));
		peligro.setNombreEn(name+ new Random().nextInt(99999999));
		peligro.setDamage(1);
		
		return peligro;
	}
	private Alert createNewAlert(String tagName){
		Alert alert=new Alert();
		alert.setNombre(tagName + + new Random().nextInt(99999999));
		alert.setLugarObj(null); 
		alert.setTexto("texto");
		alert.setTexto("textoEn");
		alert.setCaducidad(-1);
		alert.setLink1("xxxx");
		alert.setFechaPub(new Date());
		alert.setPeligro(null);
		
		return alert;
	}
	
	
}
