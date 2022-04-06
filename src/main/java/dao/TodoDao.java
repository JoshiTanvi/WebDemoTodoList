package dao;

import java.util.List;

import model.Todo;

public interface TodoDao {

	void inserTodo(Todo todo);
	
	Todo selectTodo(long todoId);
	
	List<Todo> selectAllTodos(String uname);
	
	boolean deleteTodo(int id);
	
	boolean updateTodo(Todo todo);
}
