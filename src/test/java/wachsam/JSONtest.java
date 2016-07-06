package wachsam;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.io.wachsam.model.A;



//http://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial

public class JSONtest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testTipoSitioSave() throws IOException{
	   String json_1="{\"a\":\"lion1\","
	   		         //+ "\"b\":\"lion2\","
	   		         + "\"c\":3,"
	   		         + "\"d\":\"z\"}";
	   
	   ObjectMapper objectMapper = new ObjectMapper();
	   A a = objectMapper.readValue(json_1, A.class);
		
	   System.out.println(a);
	   
	   objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	   StringWriter stringEmp = new StringWriter();
	   objectMapper.writeValue(stringEmp, a);
	   System.out.println("---> "+stringEmp);
	  
	
 	}
	// Leer metadata
	// obtener objeto
	// obtener col del json
	// sus


}
