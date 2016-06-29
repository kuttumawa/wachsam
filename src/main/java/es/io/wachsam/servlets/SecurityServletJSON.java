package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;








import com.google.gson.annotations.Expose;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.SecurityService;


/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class SecurityServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecurityServletJSON() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		SecurityService securityService=(SecurityService) context.getBean("securityService");
		OperationLogDao operationLogDao=(OperationLogDao) context.getBean("operationLogDao");
		UsuarioDao usuarioDao=(UsuarioDao) context.getBean("usuarioDao");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("accesosPorUsuarioUltimoMes")){
				List<DataForBarcode> datasForBarcode=new ArrayList<DataForBarcode>();
				List<Usuario> usuarios=usuarioDao.getAll();
				for(Usuario user:usuarios){
					DataForBarcode dataForBarcode=new DataForBarcode();
					dataForBarcode.setName(user.getLogin());
					dataForBarcode.setMentions(operationLogDao.accesosPorUsuarioUltimoMes(user.getId(),30));
		        	datasForBarcode.add(dataForBarcode);
		        }
				out.println(prettyGson.toJson(datasForBarcode));
			}
		}
		
	}
	
	class DataForBarcode{
		@Expose
		String name;
		@Expose
		List<Long> mentions;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Long> getMentions() {
			return mentions;
		}
		public void setMentions(List<Long> mentions) {
			this.mentions = mentions;
		}
			
	}

	

}
