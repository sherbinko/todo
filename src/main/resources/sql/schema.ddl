/*
    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
    Copyright 2016, Andrey Shcherbinko. All rights reserved.
*/
CREATE TABLE IF NOT EXISTS Users
(
    login varchar PRIMARY KEY,
    password text NOT NULL
);


CREATE TABLE IF NOT EXISTS Tasks
(
    id integer PRIMARY KEY,
    desc varchar NOT NULL,
    status varchar NOT NULL,
    owner varchar REFERENCES Users(login)
);
