package web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TodoDao;
import dao.TodoDaoImpl;
import model.Todo;

/**
 * Servlet implementation class TodoController
 */

@WebServlet("/")
public class TodoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TodoDao todoDao;
	private String uname = null;

	public void init() {
		todoDao = new TodoDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		//System.out.println(action);
		
		HttpSession s = request.getSession();
		uname = (String) s.getAttribute("uname");
		System.out.println("usrname from session " + uname);
		
		switch (action) {
		case "/new": {
			//System.out.println(action);
			showNewForm(request, response);
			break;
		}
		case "/insert": {
			insertTodo(request, response);
			break;
		}
		case "/delete": {
			deleteTodo(request, response);
			break;
		}
		case "/edit": {
			showEditForm(request, response);
			break;
		}
		case "/update": {
			updateTodo(request, response);
			break;
		}
		case "/list": {
			listTodo(request, response);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("todo/todo-form.jsp");
		rd.forward(request, response);
		
	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		//String username = request.getParameter("username");
		String username = uname;
		String description = request.getParameter("description");
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		LocalDate targeDate = LocalDate.parse(request.getParameter("targetDate"));
		
		Todo newTodo = new Todo(title, username, description, targeDate, isDone);
		todoDao.inserTodo(newTodo);
		response.sendRedirect("list");
	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		todoDao.deleteTodo(id);
		response.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		
		Todo existingTodo = todoDao.selectTodo(id);
		RequestDispatcher rd = request.getRequestDispatcher("todo/todo-form.jsp");
		request.setAttribute("todo", existingTodo);
		rd.forward(request, response);
		
	}

	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		long id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		//String username = uname;
		String description = request.getParameter("description");
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		LocalDate targeDate = LocalDate.parse(request.getParameter("targetDate"));
		
		Todo updateTodo = new Todo(id, title, username, description, targeDate, isDone);
		todoDao.updateTodo(updateTodo);
		response.sendRedirect("list");
		
		
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List <Todo> listTodo = todoDao.selectAllTodos(uname);
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher rd = request.getRequestDispatcher("todo/todo-list.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
