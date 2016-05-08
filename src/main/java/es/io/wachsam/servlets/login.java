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
import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.SecurityService;

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
		
		    request.getSession().setAttribute("user",null);
			String nextJSP = "/login.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
		
			
		}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		SecurityService securityService = (SecurityService) context.getBean("securityService");
		String login=request.getParameter("user");
		String password=request.getParameter("pass");
		Usuario user=securityService.login(login,password);
		if(user!=null){
		   request.getSession().setAttribute("user", login);
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
