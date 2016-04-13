package es.io.wachsam.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import es.io.wachsam.model.Airport;
import es.io.wachsam.model.Sitio;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	
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
	        while((line = in.readLine()) != null) {
	            resultado.add(n++ + ": "+processObject(objeto,line));
	        }
	        request.setAttribute("resultado",resultado);
	        String nextJSP = "/fileUpload.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	    } finally {
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
	
	private String processObject(String object,String line){
		List<String> errores=Sitio.validateCSVLine(line);
		StringBuilder sb=new StringBuilder(line);
		if(errores.size()!=0) {
			sb.append(" {ERROR ");
		    for(String s:errores){
			  sb.append("|"+s);
		    }
		    sb.append("}");
		}else{
			sb.append(" {OK}");
		}
		return sb.toString();
	}
         
	
}
