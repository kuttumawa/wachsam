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

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.AccionesSobreObjetosTipos;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.repositories.PeligroRepository;

public class OperationLogTest extends TestCase {
	@Resource
	private PeligroRepository repository;
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private DataDao dataDao;
	private TagDao tagDao;
	private OperationLogDao operationLogDao;

	@Resource
	private ElasticsearchTemplate template;
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		dataDao=(DataDao) context.getBean("dataDao");
		tagDao=(TagDao) context.getBean("tagDao");
		operationLogDao=(OperationLogDao) context.getBean("operationLogDao");
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
	public void testOperationLogSave() throws IOException{
	 assertEquals(true,true);
	 Usuario usuario=new Usuario();
	 usuario.setId(666L);
	 OperationLog op=new OperationLog(Alert.class.getName(),null,AccionesSobreObjetosTipos.ALL.name(),usuario.getId(),new Date());
	 Long id=operationLogDao.save(op);
	 assertNotNull(id);
	
 	}
	
	
	
}
