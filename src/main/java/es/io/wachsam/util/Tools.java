package es.io.wachsam.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Longs;

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
	
	public static long[] stringToLongArray(String[] ss){
		List<Long> res=new ArrayList<Long>();
		if(ss!=null){
			for(String s: ss){
				res.add(Long.parseLong(s));
			}
		}
		return Longs.toArray(res);
	}
	public static List<Long> stringToLongList(String[] ss){
		List<Long> res=new ArrayList<Long>();
		if(ss!=null){
			for(String s: ss){
				res.add(Long.parseLong(s));
			}
		}
		return res;
	}

}
