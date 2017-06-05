package com.thanethomson.demos.todo.repos;

import com.thanethomson.demos.todo.models.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "todo-lists", collectionResourceRel = "todoLists")
public interface TodoListRepo extends CrudRepository<TodoList, Long> {
}
