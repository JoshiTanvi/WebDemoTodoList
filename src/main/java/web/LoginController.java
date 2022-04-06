package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoginDao;
import model.LoginBean;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private LoginDao loginDao;
    
	public void init() {
		loginDao = new LoginDao();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath().concat("/login/login.jsp");
		response.sendRedirect(path);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);
		
		if(loginDao.validate(loginBean)) {
			request.setAttribute("NOTIFICATION", "Login Sucessfull");
			HttpSession s = request.getSession();
			s.setAttribute("uname", username);
			//RequestDispatcher rd = request.getRequestDispatcher("todo/todo-list.jsp");
			//rd.forward(request, response);
			response.sendRedirect("todo/todo-list.jsp");
		}
		else {
			request.setAttribute("NOTIFICATION", "Invalid Username or Password !");
			RequestDispatcher rd = request.getRequestDispatcher("login/login.jsp");
			rd.forward(request, response);
		}
	}
}
