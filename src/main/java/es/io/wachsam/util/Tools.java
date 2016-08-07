package es.io.wachsam.util;

import java.util.List;

public class Tools {
	
	public static String listToString(List<String> list) {
		if(list.size()==0)return null;
		StringBuffer sb=new StringBuffer();
		for(String e:list){
			sb.append(e);
			sb.append("; ");
		}
		return sb.toString(); 
		
	}

}
