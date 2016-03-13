package wachsam;



import java.io.IOException;
import java.util.List;

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

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
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
	public void testAltaDeDato() throws IOException{
	 assertEquals(true,true);
	 TagDao dao = (TagDao) context.getBean("tagDao");
	 Long id=dao.save(createNewTag("fallecidos"));
	 assertNotNull(id);
	 Tag temMax=dao.getTag(id);	 
	 Long id2=dao.save(createNewTag("diciembre"));
	 assertNotNull(id2);
	 Tag junio=dao.getTag(id2);
	 
	 Data temperaturaMaxJulioEnBangladesh = new Data();
	 temperaturaMaxJulioEnBangladesh.setLugarId(51L);
	 temperaturaMaxJulioEnBangladesh.setTag1(temMax);
	 temperaturaMaxJulioEnBangladesh.setTag2(junio);
	 temperaturaMaxJulioEnBangladesh.setValue("20");
	 temperaturaMaxJulioEnBangladesh.setTipoValor(DataValueTipo.NUMERICO);
	 
	 DataDao dataDao = (DataDao) context.getBean("dataDao");
	 dataDao.save(temperaturaMaxJulioEnBangladesh);
	 assertNotNull(temperaturaMaxJulioEnBangladesh.getId());
	 
		Data filtro=new Data();
		filtro.setTag1(temMax);
		filtro.setTag1(junio);
		List<Data> datas = dataDao.getAll(filtro);
		assertTrue(datas.size()>0);
 	}
	
	private Tag createNewTag(String tagName){
		Tag tag=new Tag();
		tag.setNombre(tagName);
		tag.setNombreEn("tagName_EN");
		tag.setDescripcion("Descripción para "+ tagName);   
		return tag;
	}
	
	
}
