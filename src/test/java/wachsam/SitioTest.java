package wachsam;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.TipoSitioService;
import junit.framework.TestCase;

public class SitioTest extends TestCase {
	@Resource
	
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private DataDao dataDao;
	private TagDao tagDao;
	private DataService dataService;
	private TipoSitioService tipoSitioService;

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
		tipoSitioService=(TipoSitioService) context.getBean("tipoSitioService");
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
	public void testTipoSitioSave() throws IOException{
	    Usuario usuario=new Usuario();
	    usuario.setId(1L);
		List<TipoSitio> tipos=null;
		tipos=tipoSitioService.getAll();
		assertNotNull(tipos);
		int tam=tipos.size();
		TipoSitio newTipo=new TipoSitio();
		newTipo.setNombre("PRUEBA_X");
		newTipo.setDescripcion("DES_X");
		try {
			TipoSitio n=tipoSitioService.save(newTipo, usuario);
		} catch (NoAutorizadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tipos=tipoSitioService.getAll();
		assertTrue(tipos.size()==tam+1);
	
 	}
	

	
}
