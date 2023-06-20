SET foreign_key_checks = 0;

DROP DATABASE IF EXISTS eventDB;
CREATE DATABASE eventDB;

USE eventDB;

DROP TABLE IF EXISTS events;

CREATE TABLE events (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255)
);

DROP TABLE IF EXISTS guests;

CREATE TABLE guests (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    studentId INT,
    FOREIGN KEY (studentId) REFERENCES universityDB.students(id)
);

INSERT INTO guests(name, studentId)
VALUES("Ola", 8);


SET foreign_key_checks = 1;

