package data;

import data.jpa.Task;
import data.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vo.Status;

import javax.persistence.EntityManager;
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

    private User getUserByLogin(String login) {
        User currUser = entityManager.find(User.class, login);

        return currUser;
    }

    public List<Task> findTasks(String currLogin) {
        List<Task> tasks = getUserByLogin(currLogin).tasks;

        return tasks;
    }

    public Long createTask(String desc, String currLogin) {
        Task t = new Task();
        t.status = Status.NEW;
        t.desc = desc;
        t.owner = getUserByLogin(currLogin);

        entityManager.persist(t);

        return t.id;
    }

    public Task getTaskById(long id) {
        return entityManager.find(Task.class, id);
    }

    public void deleteTask(long id) {
        entityManager.remove(getTaskById(id));
    }

    public void setTaskStatus(long id, Status status) {
        Task task = getTaskById(id);
        task.status = status;
        entityManager.persist(task);
    }
}
