package com.thanethomson.demos.todo.repos;

import com.thanethomson.demos.todo.models.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "todo-items", collectionResourceRel = "todoItems")
public interface TodoItemRepo extends CrudRepository<TodoItem, Long> {
}
