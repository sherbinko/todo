package data;

import data.jpa.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vo.Status;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Repository
@EntityScan(basePackages = "data.jpa")
@EnableTransactionManagement
@Transactional
public class CommonDAO {
    @Autowired
    EntityManager entityManager;

    public List<Task> findTasks() {
        Query query = entityManager.createNamedQuery("Task.findAll");
        List tasks = query.getResultList();

        return tasks;
    }

    public Long createTask(String desc) {
        Task t = new Task();
        t.status = Status.NEW;
        t.desc = desc;

        entityManager.persist(t);

        return t.id;
    }

    public void deleteTask(long id) {
        Task task = entityManager.find(Task.class, id);
        entityManager.remove(task);
    }

    public void setTaskStatus(long id, Status status) {
        Task task = entityManager.find(Task.class, id);
        task.status = status;
        entityManager.persist(task);
    }
}
