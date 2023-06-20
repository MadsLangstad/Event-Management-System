SET foreign_key_checks = 0;

DROP DATABASE IF EXISTS universityDB;
CREATE DATABASE universityDB;

USE universityDB;

DROP TABLE IF EXISTS teachers;

CREATE TABLE teachers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL

);

DROP TABLE IF EXISTS StudyPrograms;

CREATE TABLE StudyPrograms (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    programResponsible INT,
    description VARCHAR(1000),
    FOREIGN KEY (programResponsible) REFERENCES teachers(id)
);

ALTER TABLE teachers ADD studyProgramId INT;
ALTER TABLE teachers ADD CONSTRAINT _FK FOREIGN KEY (studyProgramId) REFERENCES StudyPrograms(id);

DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    studyProgramId INT,
    FOREIGN KEY (studyProgramId) REFERENCES StudyPrograms(id)
);

-- StudyPrograms --

INSERT INTO studyPrograms(name, description)
VALUES("Programming", "Programming refers to a technological process for telling a computer which tasks to perform in order to solve problems. You can think of programming as a collaboration between humans and computers, in which humans create instructions for a computer to follow (code) in a language computers can understand.");

INSERT INTO studyPrograms(name, description)
VALUES("Interaction Design", "These five essential principles of interaction design; consistency, perceivability, learnability, predictability, and feedback helps us focus on crafting better solutions, experiences, and designs.");

INSERT INTO studyPrograms(name, description)
VALUES("Cyber Security", "Cyber security is the practice of defending computers, servers, mobile devices, electronic systems, networks, and data from malicious attacks. It's also known as information technology security or electronic information security.");

INSERT INTO studyPrograms(name, description)
VALUES("Frontend & Mobile Development", "
Front-end development is the process of building components that interact with users. Examples are the user interface, buttons, user-entered data, websites, and user experience (UX) features. The front end aims at meeting user requirements and delivering a positive user experience.");


-- Teachers --

INSERT INTO teachers(name, studyProgramId)
VALUES("Lida Holtet", 2);

INSERT INTO teachers(name, studyProgramId)
VALUES("Rolando Gonzales", 1);

INSERT INTO teachers(name, studyProgramId)
VALUES("Bengt Østbye", 3);

INSERT INTO teachers(name, studyProgramId)
VALUES("Roald Simonsen", 4);



-- Students --
INSERT INTO students(name, studyProgramId)
VALUES("Phillip Hammerstad", 1);

INSERT INTO students(name, studyProgramId)
VALUES("Eirik Djønne", 4);

INSERT INTO students(name, studyProgramId)
VALUES("Jonas Bergenheim", 3);

INSERT INTO students(name, studyProgramId)
VALUES("Mads Langstad", 1);

INSERT INTO students(name, studyProgramId)
VALUES("Greta Thunberg", 2);

INSERT INTO students(name, studyProgramId)
VALUES("Steffen Krogstad", 4);

INSERT INTO students(name, studyProgramId)
VALUES("Andrew Larsen", 2);

INSERT INTO students(name, studyProgramId)
VALUES("Linda Håre", 1);

INSERT INTO students(name, studyProgramId)
VALUES("Finn schøll", 3);

INSERT INTO students(name, studyProgramId)
VALUES("Linda Håre", 4);


-- Updating --
UPDATE studyPrograms SET programResponsible = 1 WHERE id = 2;
UPDATE studyPrograms SET programResponsible = 2 WHERE id = 1;
UPDATE studyPrograms SET programResponsible = 4 WHERE id = 3;
UPDATE studyPrograms SET programResponsible = 3 WHERE id = 4;

SET foreign_key_checks = 1;
