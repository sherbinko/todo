package services;

import data.CommonDAO;
import data.jpa.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Service
public class AuthService {
    @Autowired
    CommonDAO dao;

    public String getCurrLogin() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        return login;
    }

    public void checkTaskOwner(Task task) {
        String currLogin = getCurrLogin();
        String taskOwner = task.owner.login;

        if (!taskOwner.equals(currLogin)) {
            throw new vo.SecurityException(
                    String.format("Task owner %s does not match the logged user %s", taskOwner, currLogin));
        }
    }

    public void checkTaskOwner(Long taskId) {
        checkTaskOwner(dao.getTaskById(taskId));
    }
}
