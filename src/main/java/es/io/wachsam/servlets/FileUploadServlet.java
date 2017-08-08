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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import es.io.wachsam.dao.FactorDao;
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.NivelProbabilidad;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.FileUploadService;

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
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");		
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
	    response.setContentType("text/html;charset=UTF-8");
	    context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
	    final Part filePart = request.getPart("file");
	    final String objeto = request.getParameter("objeto");
	    final String separador = request.getParameter("separador");
	    boolean actualizaObjeto = false;
	    actualizaObjeto=request.getParameter("actualizaObjeto")!=null?true:false;

	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	    List<String> errores=new ArrayList<String>();
	    FileUploadService fileUploadService=(FileUploadService) context.getBean("fileUploadService");
	    try {
	        filecontent = filePart.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(filecontent,"ISO-8859-1"));
	        String line = null;	       
	        int n=1;
	        List<String> csv =new ArrayList<String>();
	       	while((line = in.readLine()) != null) {
	        		if(line.length()>0 && !line.matches("^//.*")) csv.add(line);
		    }
	       	fileUploadService.procesarMetadata(csv,objeto,errores);
	        Map<Object,List<Data>> dat=null;
	       	
	        if(errores.size()==0)
	       	  dat=fileUploadService.cargarCsv(ObjetoSistema.valueOf(objeto),csv,errores,separador);	
	       	
	       	if(errores.size()>0){
	        	 request.setAttribute("resultado",errores);
	        }else{
	        	try{
	        	  fileUploadService.save(dat,objeto,usuario,actualizaObjeto);
	        	}catch(Exception e){
	        		if(e.getCause()!=null)errores.add(e.getCause().getCause().toString());
	        		else errores.add(e.toString());
	        	}
	        	if(errores.size()<1)errores.add("CargadoOK objeto: "+objeto + "["+dat.size()+"]");
	        }
	        request.setAttribute("resultado",errores);
	        String nextJSP = "/fileUpload.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
	       
	    }catch (Exception e){
	    	errores.add(e.toString());
	    	request.setAttribute("resultado",errores);
	    	String nextJSP = "/fileUpload.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
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
	
	
	public SitioDao getSitioDao() {
		return sitioDao;
	}
	public void setSitioDao(SitioDao sitioDao) {
		this.sitioDao = sitioDao;
	}
         
	
}
