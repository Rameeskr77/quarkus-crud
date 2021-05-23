package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Films extends PanacheEntity {
    @Column(length = 50)
  public String name;
@Column(length = 50)
    public String description;
}
