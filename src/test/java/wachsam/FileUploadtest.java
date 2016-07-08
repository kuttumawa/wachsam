package wachsam;



import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.A;
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
	
	@Test
	public void testCSVtoObject() throws IOException{
		String[] sitiosConDataCsv={
				"A:objetoId(1),A:objetoTipo(1),A:tag(num_casos),A:tipoValor(2),value,F:Guess_text_to_lugar(connectToId),A:objetoConnectedTipo(2)",
				"12,BRASIL",
				"12,Burundi"
		};
		//Algorithm
		//Get Object and init and instance
		//Get metadata from index 0
		//split csv line
		//Parse each column
		//Create the object
		
		Sitio sitio=new Sitio();
		String[] metadadata=sitiosConDataCsv[0].split(",");
		for(String datum:metadadata){
			if(datum.startsWith("A:")){
				datum=datum.replace(datum,"");
			}
			else if(datum.startsWith("F:")){
								
			}else{
				
			}
		}
		//Populate Object
		    
		    
		
		
		System.out.println(metadadata);
	
 	}
	
	@Test
	public void testPopulateClassWithData(){
		A a=new A();
		Field field=null;
		try {
			field = a.getClass().getDeclaredField("a");
			Field field2 = a.getClass().getDeclaredField("d");
			field.getType();
			field2.getType();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			field.set(a,"1");
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
