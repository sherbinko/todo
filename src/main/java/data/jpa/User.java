package data.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Entity
@Table(name = "Users")
public class User {
    @Id
    public String login;

    @OneToMany(targetEntity = Task.class, mappedBy = "owner", orphanRemoval = true)
    public List<Task> tasks;
}
