package wachsam;



import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.services.FileUploadService;
import junit.framework.TestCase;

public class FileUploadtest extends TestCase {

	
    FileUploadService fileUploadService=null;
	
	
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		context = new 
                ClassPathXmlApplicationContext("applicationContext_test.xml");
		fileUploadService=(FileUploadService) context.getBean("fileUploadService");
	}

	protected void tearDown() throws Exception {
		
	}

	
	@Test
	public void testCheckMetadata(){
		String objeto="Sitio";
		String[] csv={
				"Lugar->A:nombreEn(VOID),nombre,D:num_camas_por1000,direccion,A:textoEn(NUMB),"
				+ "valoracion,tipo,F:ISO_3166_1_alpha2_to_lugar(lugarObj),D:director",
				"Hos1,456,calle hos1,123,9,BR,Vodox",
				"Hos2,33,calle hos2,1234,97,TD,Daniel Bloom"
		};
		List<String> errores=new ArrayList<String>();
		fileUploadService.procesarMetadata(Arrays.asList(csv),objeto,errores);
		assertEquals(1,errores.size());
	}		
		 	                 
	

	@Test
	public void testCSVtoObjectSitio() throws IOException{
		
		String[] sitiosConDataCsv={
				"A:nombreEn(VOID),nombre,D:num_camas_por1000,direccion,A:textoEn(NUMB),"
				+ "valoracion,tipo,F:ISO_3166_1_alpha2_to_lugar(lugarObj),D:director",
				"Hos1,456,calle hos1,123,9,BR,Vodox",
				"Hos2,33,calle hos2,1234,97,TD,Daniel Bloom"
		};
		
		List<String> errores=new ArrayList<String>();
		Map<Object,List<Data>> dat=fileUploadService.cargarCsv(ObjetoSistema.Sitio, sitiosConDataCsv, errores);
		
		assertEquals(0,errores.size());
	    List sitios = new ArrayList(dat.keySet());
	    Sitio sitio=(Sitio) sitios.get(0);
		assertTrue(sitio.getNombre().equals("Hos1"));
		assertTrue(sitio.getDireccion().equals("calle hos1"));
		assertTrue(sitio.getValoracion().equals(123));
		assertTrue(sitio.getTipo().getId().equals(9L));
		assertTrue(sitio.getLugarObj().getId().equals(34L));
		assertTrue(sitio.getNombreEn().equals("VOID"));
		assertTrue(sitio.getTextoEn().equals("NUMB"));
		assertTrue(dat.get(sitio).get(0).getValue().equals("456"));
		assertTrue(dat.get(sitio).get(1).getValue().equals("Vodox"));
		
		sitio=(Sitio) sitios.get(1);
		assertTrue(sitio.getNombre().equals("Hos2"));
		assertTrue(sitio.getDireccion().equals("calle hos2"));
		assertTrue(sitio.getValoracion().equals(1234));
		assertTrue(sitio.getTipo().getId().equals(97L));
		assertTrue(sitio.getLugarObj().getId().equals(4L));
		assertTrue(sitio.getNombreEn().equals("VOID"));
		assertTrue(sitio.getTextoEn().equals("NUMB"));
		assertTrue(dat.get(sitio).get(0).getValue().equals("33"));
		assertTrue(dat.get(sitio).get(1).getValue().equals("Daniel Bloom"));
	
	
 	}
	/**
	 * @throws IOException
	 * Carga Datos nº de casos de Malaria en el mundo en 2015
	 */
	@Test
	public void testCSVtoData() throws IOException{
		
		String[] sitiosConDataCsv={
				"A:objetoId(F:texto_to_peligro(Malaria)),A:objetoTipo(Peligro),A:tag(F:text_to_tag(num_casos_2015)),A:tipoValor(NUMERICO),"
				+ "value,F:texto_to_lugar(connectToId),A:objetoConnectedTipo(Lugar)",
				"12,BRASIL",
				"1234,CHAD"
		};
		
		
		
		List<String> errores=new ArrayList<String>();
		Map<Object,List<Data>> dat=fileUploadService.cargarCsv(ObjetoSistema.Data, sitiosConDataCsv, errores);
		
		assertEquals(0,errores.size());
	    List datas = new ArrayList(dat.keySet());
	    Data data=(Data) datas.get(0);
		assertTrue(data.getValue().equals("12"));
		assertTrue(data.getConnectToId().equals(62L));
		assertTrue(data.getObjetoId().equals(269L));
		assertTrue(data.getObjetoTipo().equals(ObjetoSistema.Peligro));
		assertTrue(data.getTag().getId().equals(58L));
		assertTrue(data.getObjetoConnected().equals(ObjetoSistema.Lugar));
		assertTrue(data.getTipoValor().equals(DataValueTipo.NUMERICO));
		
	
	
 	}
	
	/**
	 * @throws IOException
	 * Carga para lugares poblacion,idioma,
	 */
	@Test
	public void testCSVtoMultipleSimpleData() throws IOException{
		
		String[] csv={
				"F:texto_to_lugar(id),D:poblacion,D:idioma",
				"BRASIL,300000000,portuguese",
				"CHAD,10000000,french"
		};
		
		
		List<String> errores=new ArrayList<String>();
		Map<Object,List<Data>> dat=fileUploadService.cargarCsv(ObjetoSistema.Lugar, csv, errores);
		
		assertEquals(0,errores.size());
	    List datas = new ArrayList(dat.keySet());
	    Lugar lugar=(Lugar) datas.get(0);   
		assertTrue(dat.get(lugar).get(0).getValue().equals("300000000"));
		assertTrue(dat.get(lugar).get(1).getValue().equals("portuguese"));

		
	
	
 	}
	
	/**
	 * @throws IOException
	 * Modifica objeto lugares
	 */
	@Test
	public void testModificarLugarNombreEn() throws IOException{
		
		String[] csv={
				"F:texto_to_lugar(id),nombreEn",
				"BRASIL,BRAZIL_EN",
				"CHAD,CHAD_EN"
		};	
		List<String> errores=new ArrayList<String>();
		Map<Object,List<Data>> dat=fileUploadService.cargarCsv(ObjetoSistema.Lugar, csv, errores);
		
		assertEquals(0,errores.size());
	    List datas = new ArrayList(dat.keySet());
	    Lugar lugar=(Lugar) datas.get(0);   
		assertTrue(lugar.getNombreEn().equalsIgnoreCase("BRAZIL_EN"));
	
 	}
	
	/**
	 * @throws IOException
	 * Modifica objeto Peligro
	 */
	@Test
	public void testModificarPeligroNombreEn() throws IOException{
		
		String[] csv={
				"F:texto_to_peligro(id),nombreEn",
				"Aedes aegypti,Aedes_EN",
				"Aduanas,Aduanas_EN"
		};	
		List<String> errores=new ArrayList<String>();
		Map<Object,List<Data>> dat=fileUploadService.cargarCsv(ObjetoSistema.Peligro, csv, errores);
		
		assertEquals(0,errores.size());
	    List datas = new ArrayList(dat.keySet());
	    Peligro peligro=(Peligro) datas.get(0);   
		assertTrue(peligro.getNombreEn().equalsIgnoreCase("Aedes_EN"));
	
 	}


	
	
}
