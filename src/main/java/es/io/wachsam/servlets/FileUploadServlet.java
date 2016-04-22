package es.io.wachsam.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.AirportDao;
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.model.Sitio;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	private SitioDao sitioDao;
	WebApplicationContext context=null;
	private static final long serialVersionUID = -3650360070721120988L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		
		String nextJSP = "/fileUpload.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
	protected void doPost(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");
	    context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
	    final Part filePart = request.getPart("file");
	    final String objeto = request.getParameter("objeto");
	    

	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	    List<String> resultado=new ArrayList<String>();

	    try {
	        filecontent = filePart.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(filecontent,"ISO-8859-1"));
	        String line = null;	       
	        int n=1;
	        Boolean error=null;
	        try{
	        	String log="";
	        	while((line = in.readLine()) != null) {
		            try{
		            	validateObject(objeto,line);
		            	log = procesarObject(objeto,line);
		            }catch(Exception e){
		            	log=e.getMessage();
		            	resultado.add(n++ + ": "+log);
		            	break;
		            }
	        		
		            resultado.add(n++ + ": "+log);
		        }
	        	
	        }catch (UnsupportedOperationException e){
		    	resultado.add(e.getMessage());
		    }
	        
	        request.setAttribute("resultado",resultado);
	        String nextJSP = "/fileUpload.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
	       
	    }catch (Exception e){
	    	request.setAttribute("resultado",e.getMessage());
	    }finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	    
	}
    private void readLinea(File theFile) throws IOException{
    	LineIterator it = FileUtils.lineIterator(theFile, "ISO-8859-1");
    	try {
    	    while (it.hasNext()) {
    	        String line = it.nextLine();
    	        // do something with line
    	    }
    	} finally {
    	    LineIterator.closeQuietly(it);
    	}
    	
    }
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private String validateObject(String object,String line) throws Exception{
		List<String> errores=null;
		if(object.equalsIgnoreCase("sitio")) errores=Sitio.validateCSVLine(line);
		else {
			throw new UnsupportedOperationException("No existe la operación por  el momento");
		}
		
		StringBuilder sb=new StringBuilder(line);
		if(errores.size()!=0) {
			sb.append(" {ERROR ");
		    for(String s:errores){
			  sb.append("|"+s);
		    }
		    sb.append("}");
		    throw new Exception(sb.toString());
		}else{
			sb.append(" {OK}");
		}
		return sb.toString();
	}
	private String procesarObject(String object,String line){
		String l=null;
		if(object.equalsIgnoreCase("sitio")){
			if(sitioDao==null) sitioDao = (SitioDao) context.getBean("sitioDao");
			Sitio sitio=new Sitio(line.split(";"));
			sitioDao.save(sitio);
			l=line + "{ GRABADO} id: " + sitio.getId();
		}
		else {
			throw new UnsupportedOperationException("No existe la operación por  el momento");
		}
		
		return l;
	}
	public SitioDao getSitioDao() {
		return sitioDao;
	}
	public void setSitioDao(SitioDao sitioDao) {
		this.sitioDao = sitioDao;
	}
         
	
}
