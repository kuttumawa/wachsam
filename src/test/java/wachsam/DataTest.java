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
import es.io.wachsam.model.Tag;
import es.io.wachsam.repositories.PeligroRepository;

public class DataTest extends TestCase {
	@Resource
	private PeligroRepository repository;
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private DataDao dataDao;
	private TagDao tagDao;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		dataDao=(DataDao) context.getBean("dataDao");
		tagDao=(TagDao) context.getBean("tagDao");
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
	 Tag tag2=createNewTag("tag2");	
	 Tag tag3=createNewTag("tag3");	
	 tagDao.save(tag1);
	 tags.add(tag1);
	 tagDao.save(tag2);
	 tags.add(tag2);
	 tagDao.save(tag3);
	 tags.add(tag3);
	 Data data1=new Data("22","void",DataValueTipo.NUMERICO,tag1,tag2,tag3,1L,null,null,null);
	 dataDao.save(data1);
	 datas.add(data1);
	 List<Data> resultado=dataDao.getAll(data1);
	 assertTrue(resultado.size()==1);
	 Data data2=new Data("22","void",DataValueTipo.NUMERICO,tag1,tag2,null,1L,null,null,null);
	 resultado=dataDao.getAll(data2);
	 assertTrue(resultado.size()==0);
	 Data data3=new Data("22","void",DataValueTipo.NUMERICO,tag2,tag3,tag1,1L,null,null,null);
	 resultado=dataDao.getAll(data3);
	 assertTrue(resultado.size()==1);
	 
	
 	}
	
	private Tag createNewTag(String tagName){
		Tag tag=new Tag();
		tag.setNombre(tagName);
		tag.setNombreEn("tagName_EN"+ new Random().nextInt(99999999));
		tag.setDescripcion("Descripción para "+ tagName);   
		return tag;
	}
	
	
}
