package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("user")!=null){
			  String nextJSP = request.getContextPath()+ "/ProvisionalAlertUpdaterForYou";
			  response.sendRedirect(nextJSP);
			
		}else{
			String nextJSP = "/login.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
		}
			
		}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		String _usuario="richardfrancisburton";
		String _password="monk267";
		String nombre=request.getParameter("user");
		String password=request.getParameter("pass");
		if(nombre!=null && password!=null && nombre.equals(_usuario) && password.equals(_password)){
		   request.getSession().setAttribute("user", nombre);
		   String nextJSP = request.getContextPath()+ "/ProvisionalAlertUpdaterForYou";
		   response.sendRedirect(nextJSP);	
		}else{
		    request.setAttribute("resultado","NO te está permitido el paso");
			String nextJSP = "/login.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
		}
	}
	

}
