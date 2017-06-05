package com.thanethomson.demos.todo.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class ModelBase {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    public Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    public Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    public Date updated;

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        ModelBase o = (ModelBase)other;
        return o.id.equals(this.id);
    }

}
