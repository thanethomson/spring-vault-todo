package com.thanethomson.demos.todo.models;

import com.thanethomson.demos.todo.enums.TodoItemState;

import javax.persistence.*;

@Entity
@Table(name = "todo_items")
public class TodoItem extends ModelBase {

    /**
     * The parent to-do list to which this item belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id", nullable = false)
    public TodoList todoList;

    /**
     * The title of this item.
     */
    @Column(name = "title", nullable = false)
    public String title;

    /**
     * In which state is this to-do item at present?
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    public TodoItemState state;

    public TodoItem() {}

    public TodoItem(TodoList todoList, String title) {
        this.todoList = todoList;
        this.title = title;
        this.state = TodoItemState.OPEN;
    }

}
