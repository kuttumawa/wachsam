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
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.repositories.PeligroRepository;

public class FileUploadtest extends TestCase {
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

	/**
	 * [nommbre;nombreEn;direccion;tipo;texto;textoEn;lugarId*;valoracion]
	 */
	@Test
	public void testSitioCSVvaliation() throws IOException{
		String csvSitioLineConError[]={"nommbre;nombreEn","nommbre;nombreEn;direccion;1;texto;textoEn;22;7","nommbre;nombreEn;direccion;1;texto;textoEn;AFGANISTAN;7"};
		int csvSitioLineNumeroDeErroresEsperados[]={1,1,1};
		List<String> errores=null;
		for(int i=0;i<csvSitioLineConError.length;i++){
			errores=Sitio.validateCSVLine(csvSitioLineConError[i]);
			assertEquals(csvSitioLineNumeroDeErroresEsperados[i],errores.size());
		}
	
 	}
	
}
