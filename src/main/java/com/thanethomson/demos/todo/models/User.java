package com.thanethomson.demos.todo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends ModelBase {

    /**
     * This user's email address, which also acts as their login username.
     */
    @Column(name = "email", nullable = false, unique = true)
    public String email;

    /**
     * Allows us to store a hash of the user's password.
     */
    @Column(name = "password_hash", nullable = false)
    public String password;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "is_admin")
    public Boolean isAdmin;

    public User() {}

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        // not an administrator by default
        this.isAdmin = false;
    }

    public User(String email, String password, String firstName, String lastName, Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

}
