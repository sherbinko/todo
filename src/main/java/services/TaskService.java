package services;

import data.CommonDAO;
import data.jpa.Task;
import dto.TaskDTO;
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
public class TaskService {
    @Autowired
    CommonDAO dao;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> allTasks() {
        List<TaskDTO> dtos = new ArrayList<>();
        for (Task t : dao.findTasks()) {
            dtos.add(new TaskDTO(t.id, t.desc, t.status));
        }

        return dtos;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.PUT)
    public Long addTask(@RequestBody String desc) {
        Long id = dao.createTask(desc);

        return id;
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable Long id) {
        dao.deleteTask(id);
    }

    @RequestMapping(value = "/tasks/{id}/status", method = RequestMethod.POST)
    public void changeStatus(@PathVariable Long id, @RequestBody String status) {
        dao.setTaskStatus(id, Status.valueOf(status));
    }
}
