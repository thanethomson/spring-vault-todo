package com.thanethomson.demos.todo.repos;

import com.thanethomson.demos.todo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface UserRepo extends CrudRepository<User, Long> {

    User findFirstByEmail(String email);

}
