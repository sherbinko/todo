package services;

import data.CommonDAO;
import data.jpa.Task;
import dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vo.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@RestController
@RequestMapping(path = "/services")
public class TaskService {
    public static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    CommonDAO dao;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> allTasks() {
        List<TaskDTO> dtos = new ArrayList<>();
        String currLogin = authService.getCurrLogin();

        log.info("Reading all tasks for the user \"{}\" ... ", currLogin);

        for (Task t : dao.findTasks(currLogin)) {
            dtos.add(new TaskDTO(t.id, t.desc, t.status));
        }

        return dtos;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.PUT)
    public Long addTask(@RequestBody String desc) {
        Long id = dao.createTask(desc, authService.getCurrLogin());
        log.info("Task #{} has been added", id);

        return id;
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable Long id) {
        authService.checkTaskOwner(id);
        dao.deleteTask(id);
        log.info("Task #{} has been deleted", id);
    }

    @RequestMapping(value = "/tasks/{id}/status", method = RequestMethod.POST)
    public void changeStatus(@PathVariable Long id, @RequestBody String status) {
        authService.checkTaskOwner(id);
        dao.setTaskStatus(id, Status.valueOf(status));
        log.info("Task #{} has been modified. New Status = {}", id, status);
    }
}
