package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Todo;
import utils.JDBConnect;

public class TodoDaoImpl implements TodoDao {

	private static final String insert_sql = "insert into WebApp.todos"
			+ " (title, username, description, target_date, is_done) " + " values (?, ?, ?, ?, ?)";

	private static final String select_sql = "select id, title,username, description, target_date, is_done"
			+ " from WebApp.todos where id = ?";

	private static final String selectAll_sql = "select * from WebApp.todos where username = ?";
	
	private static final String delete_sql = "delete from WebApp.todos where id = ?";
	
	private static final String update_sql = "update WebApp.todos set "
			+ "title = ?, description = ?, target_date = ?, is_done =? where id=?";
	
	public TodoDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void inserTodo(Todo todo) {
		// TODO Auto-generated method stub
		System.out.println(insert_sql);

		try {

			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(insert_sql);
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getUsername());
			pstmt.setString(3, todo.getDescription());
			pstmt.setDate(4, Date.valueOf(todo.getTargetDate()));
			pstmt.setBoolean(5, todo.isStatus());

			System.out.println(pstmt);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Todo selectTodo(long todoId) {
		// TODO Auto-generated method stub
		Todo todo = null;

		try {

			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(select_sql);
			pstmt.setLong(1, todoId);

			System.out.println(pstmt);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_Done");

				todo = new Todo(id, title, username, description, targetDate, isDone);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todo;

	}

	@Override
	public List<Todo> selectAllTodos(String uname) {
		// TODO Auto-generated method stub
		
		List <Todo> todos = new ArrayList<>();
		
		try {
			
			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(selectAll_sql);
			pstmt.setString(1, uname);
			
			System.out.println(pstmt);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todos.add(new Todo(id, title, username, description, targetDate, isDone));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return todos;
	}

	@Override
	public boolean deleteTodo(int id) {
		// TODO Auto-generated method stub
		boolean rowDeleted = false;
		
		try {
			
			Connection con  = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(delete_sql);
			pstmt.setInt(1, id);
			
			rowDeleted = pstmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowDeleted;
	}

	@Override
	public boolean updateTodo(Todo todo) {
		// TODO Auto-generated method stub	
		boolean rowUpdated = false;
		
		try {
			
			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(update_sql);
			pstmt.setString(1, todo.getTitle());
			//pstmt.setString(2, todo.getUsername());
			pstmt.setString(2, todo.getDescription());
			pstmt.setDate(3, Date.valueOf(todo.getTargetDate()));
			pstmt.setBoolean(4, todo.isStatus());
			pstmt.setLong(5, todo.getId());
			
			System.out.println(pstmt);
			
			rowUpdated = pstmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowUpdated;
	}

}
