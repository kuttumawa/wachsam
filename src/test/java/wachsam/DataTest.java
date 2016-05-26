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
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.NodeAndLinks;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.repositories.PeligroRepository;
import es.io.wachsam.services.DataService;

public class DataTest extends TestCase {
	@Resource
	private PeligroRepository repository;
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private DataDao dataDao;
	private TagDao tagDao;
	private DataService dataService;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		dataDao=(DataDao) context.getBean("dataDao");
		tagDao=(TagDao) context.getBean("tagDao");
		dataService=(DataService) context.getBean("dataService");
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
		
	}

	@Test
	public void testTagSave() throws IOException{
	 assertEquals(true,true);
	 TagDao dao = (TagDao) context.getBean("tagDao");
	 Long id=dao.save(createNewTag("prueba"));
	 assertNotNull(id);
	 Tag tag2=dao.getTag(id);	 
	 assertNotNull(tag2);
	 try {
		dao.deleteById(id);
	 } catch (Exception e) {
		fail();
	 }
 	}
	
	@Test
	public void testAltaDeDatosYRecuperacion() throws IOException{
	 Tag tag1=createNewTag("tag1");
	 tagDao.save(tag1);
	 //tags.add(tag1);
	 Data data1=createNewdata(tag1);
	 //datas.add(data1);
	 int sizebefore=dataDao.getAll().size();
	 dataDao.save(data1);
	 assertTrue(sizebefore+1 == dataDao.getAll().size());	
 	}
	
	private Tag createNewTag(String tagName){
		Tag tag=new Tag();
		tag.setAlias("alias");
		tag.setNombre(tagName);
		tag.setNombreEn("tagName_EN"+ new Random().nextInt(99999999));
		tag.setDescripcion("Descripción para "+ tagName);   
		return tag;
	}
	private Data createNewdata(Tag tag){
		Data data=new Data();
		data.setObjetoId(new Random().nextInt()+ 1L);
		data.setObjetoTipo(ObjetoSistema.Alert);
		data.setValue("xxxx");
		data.setTipoValor(DataValueTipo.TEXTO);
		data.setTag(tag);
		return data;
	}
	@Test
	public void testGetAllNodeAndLinksForObject(){
		Usuario usuario=new Usuario();
		usuario.setId(1L);
		NodeAndLinks res= dataService.getAllNodeAndLinksForObject(1L, ObjetoSistema.Alert, usuario, new NodeAndLinks());
	    System.out.println(res);
	}
	
	
}
