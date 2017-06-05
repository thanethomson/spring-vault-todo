package com.thanethomson.demos.todo.models;

import com.thanethomson.demos.todo.enums.TodoListState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "todo_lists")
public class TodoList extends ModelBase {

    /**
     * A short, descriptive name for this list.
     */
    @Column(name = "name", nullable = false)
    public String name;

    /**
     * The users who have access to view/modify this list.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "todo_lists_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "todo_list_id", referencedColumnName = "id")
    )
    public List<User> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    public TodoListState state;

    public TodoList() {}

    public TodoList(String name, List<User> users) {
        this.name = name;
        this.users = users;
        this.state = TodoListState.ACTIVE;
    }

}
