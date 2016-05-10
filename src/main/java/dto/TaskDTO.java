package dto;

import vo.Status;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
public class TaskDTO {
    public long id;
    public String desc;
    public Status status;

    public TaskDTO(long id, String desc, Status status) {
        this.id = id;
        this.desc = desc;
        this.status = status;
    }
}
