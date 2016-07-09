package wachsam;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.io.wachsam.model.A;
import es.io.wachsam.model.Sitio;



//http://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial

public class JSONtest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testThis() throws IOException{
	   String json_1="{\"a\":\"lion1\","
	   		         //+ "\"b\":\"lion2\","
	   		         + "\"c\":\"lion3\"}";
	   
	   ObjectMapper objectMapper = new ObjectMapper();
	   A a = objectMapper.readValue(json_1, A.class);
		
	   System.out.println(a);
	   
	   objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	   StringWriter stringEmp = new StringWriter();
	   objectMapper.writeValue(stringEmp, a);
	   System.out.println("---> "+stringEmp);
	  
	
 	}
	@Test
	public void testIntrospection(){
		List<Field> privateFields = new ArrayList<Field>();
	    Field[] allFields = Sitio.class.getDeclaredFields();
	    for (Field field : allFields) {
	        //if (Modifier.isPrivate(field.getModifiers())) {
	            privateFields.add(field);
	            System.out.println(field.getName() + "-" + field.getType());
	       // }
	    }
		
		
	}
	


}
