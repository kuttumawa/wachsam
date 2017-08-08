package wachsam;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Tag;
import junit.framework.TestCase;

public class MetalenguajeTest extends TestCase {
	@Resource
	
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

	final String TEXTO_1="<brote></brote>En un nuevo informe sobre la situación de la fiebre amarilla en Uganda, " 
			+ " los medios de comunicación locales alertan de 3 nuevas  muertes de fiebre amarilla,"
			+ " con lo que el total asciende a <fallecidos>10</fallecidos>. Las tres muertes eran <fallecidos>20</fallecidos> residentes de Bukakata en el distrito de Masaka.";
	
	
	@Test
	public void testProcesarTag(){
	    List<Tag> tags=tagDao.getAll();
		List<Data> datas=procesarTextoYExtraerData(TEXTO_1,tags);
		assertTrue(datas.size()==1);
		
	}
	
	public List<Data> procesarTextoYExtraerData(String texto,List<Tag> tags){
		String TAGREGEX="(<.*?>)(.*?)(<\\/.*?>)";
		Pattern pattern = Pattern.compile(TAGREGEX, Pattern.COMMENTS);
	    Matcher matcher = pattern.matcher(texto);
	    HashMap<String,String> result=new HashMap<String, String>();
	    StringBuffer sb=new StringBuffer();
	    while (matcher.find()) {
	       result.put(matcher.group(1), matcher.group(2));
	    }
	 	List<Data> newdatas=new ArrayList<Data>();
		Iterator it = result.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        String t=pair.getKey().toString().replaceAll("<","").replaceAll(">","");
		        for(Tag tag:tags){
		        	String tagnombre=tag.getNombre().replaceAll("\\s","");
		        	if(tagnombre.equalsIgnoreCase(t.replaceAll("\\s",""))){
		        		Data dataTemp=new Data();
		        		dataTemp.setTag(tag);
		        		dataTemp.setValue(pair.getValue().toString());
		        		dataTemp.setTipoValor(DataValueTipo.TEXTO);
		        		newdatas.add(dataTemp);
		        		texto=texto.replace(pair.getKey().toString(), "xxx");
		        		texto=texto.replace("</"+t+">", "yyy");
		        	}
		        }
		    }
		System.out.println(texto);
		System.out.println(newdatas);
		return newdatas;
	}
	
	
	
	private Tag createNewTag(String tagName){
		Tag tag=new Tag();
		tag.setNombre(tagName);
		tag.setNombreEn("tagName_EN"+ new Random().nextInt(99999999));
		tag.setDescripcion("Descripción para "+ tagName);   
		return tag;
	}
	
	
}
