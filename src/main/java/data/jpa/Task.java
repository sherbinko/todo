package data.jpa;

import vo.Status;

import javax.persistence.*;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Entity
@Table(name = "Tasks")
@NamedQuery(name="Task.findAll", query="select t from Task t")
public class Task {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String desc;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Status status;
}
