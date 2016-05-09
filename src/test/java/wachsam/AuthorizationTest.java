package wachsam;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;
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
import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.AccionesSobreObjetosTipos;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.CategoriaPeligro;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.repositories.PeligroRepository;
import es.io.wachsam.services.SecurityService;

public class AuthorizationTest extends TestCase {
	@Resource
	private PeligroRepository repository;
	private List<Tag> tags=new ArrayList<Tag>();
	private List<Data> datas=new ArrayList<Data>();
	private List<Sitio> sitios=new ArrayList<Sitio>();
	private List<Usuario> usuarios=new ArrayList<Usuario>();
	private List<Permiso> permisos=new ArrayList<Permiso>();
	private List<Alert> alertas=new ArrayList<Alert>();
	private DataDao dataDao;
	private TagDao tagDao;
	private SitioDao sitioDao;
	private AlertasDao alertDao;
	private LugarDao lugarDao;
	private PeligroDao peligroDao;
	private CacheManager cacheManager;
	private UsuarioDao usuarioDao;
	private SecurityService securityService;
	


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
		usuarioDao=(UsuarioDao) context.getBean("usuarioDao");
		securityService=(SecurityService) context.getBean("securityService");
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
		for(Usuario usuario:usuarios){
			usuarioDao.deleteById(usuario.getId());
		}
		for(Permiso permiso:permisos){
			usuarioDao.deleteByIdPermiso(permiso.getId());
		}
		for(Alert alerta:alertas){
			alertDao.deleteById(alerta.getId());
		}
		
	}

	@Test
	public void testLogin() throws IOException{
	 Usuario user=new Usuario();
	 user.setLogin("user1");
	 user.setPassword("pruebapass");
	 user.setEmail("user1@email.com");
	 usuarioDao.save(user);
	 usuarios.add(user);
	 assertTrue(usuarioDao.getAll().size()>0);
	 assertTrue(securityService.login("xxx", "password")==null);
	 assertTrue(securityService.login("user1", "pruebapass")!=null);
	}
	
	@Test
	public void testAuthorizationToCreateAlert() throws IOException{
		Permiso p1=new Permiso("Todos los permisos sobre alertas2",Alert.class,AccionesSobreObjetosTipos.ALL,true,"{\"peligro\":[123,146,666]}");
		usuarioDao.savePermiso(p1);
		permisos.add(p1);
		Permiso p2=new Permiso("Lectura sobre Lugares",Lugar.class,AccionesSobreObjetosTipos.READ,false,null);
		usuarioDao.savePermiso(p2);		
		permisos.add(p2);
		assertTrue(usuarioDao.getAllPermiso().size()>0);
		Usuario user=new Usuario();
		user.setLogin("user1");
		user.setPassword("pruebapass");
		user.setEmail("user1@email.com");		
		user.addPermiso(p1);
		user.addPermiso(p2);
		usuarioDao.save(user);
		usuarios.add(user);
		Alert alert = crearAlerta(45L);
		assertFalse(alert.hasPermisos(user, AccionesSobreObjetosTipos.CREATE));
		Alert alert2 = crearAlerta(666L);
		assertTrue(alert2.hasPermisos(user, AccionesSobreObjetosTipos.CREATE));
		
	
	}
	private Alert crearAlerta(Long id){
		Alert alert =new Alert();
		Peligro peligro=new Peligro(id,"PELIGRO_"+id,"",CategoriaPeligro.conflicto,12);
		alert.setPeligro(peligro);
		return alert;
	}
	
	
	
	
}
