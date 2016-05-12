package data.jpa;

import vo.Status;

import javax.persistence.*;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String desc;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Status status;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    public User owner;
}
