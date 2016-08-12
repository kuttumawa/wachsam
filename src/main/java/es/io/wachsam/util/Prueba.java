package es.io.wachsam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba {

	public static void main(String[] args) {
		String[] x={
				"Sitio->A:nombreEn(VOID),nombre,D:num_camas_por1000,direccion,A:textoEn(NUMB),"
				+ "valoracion,tipo,F:ISO_3166_1_alpha2_to_lugar(lugarObj),D:director",
				"Hos1,456,calle hos1,123,9,BR,Vodox",
				"Hos2,33,calle hos2,1234,97,TD,Daniel Bloom"};
		// String to be scanned to find the pattern.
	      String line = "Lugar->A:nombreEn(VOID),nombre,D:num_camas_por1000,";
	      String pattern = "^(.*)->";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	    	 System.out.println(line);
	         System.out.println("Found : " + m.group(0) );
	         System.out.println("Found : " + m.group(1) );
	         System.out.println(m.replaceAll(""));
	         
	      } else {
	         System.out.println("NO MATCH");
	      }
	}

}
