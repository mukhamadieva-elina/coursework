CREATE TABLE goods (
    ID serial not null,
    NAME varchar(50),
    PRIORITY real,
    PRIMARY KEY (ID)
);
CREATE TABLE sales (
    ID serial not null,
    GOOD_ID integer,
    GOOD_COUNT integer,
    CREATE_DATE timestamp(3),
    PRIMARY KEY (ID),
    FOREIGN KEY (GOOD_ID) REFERENCES goods(ID)
);
CREATE TABLE warehouse1(
    ID serial not null,
    GOOD_ID integer,
    GOOD_COUNT integer,
    PRIMARY KEY (ID),
    FOREIGN KEY (GOOD_ID) REFERENCES goods(ID)
);
CREATE TABLE warehouse2(
    ID serial not null,
    GOOD_ID integer,
    GOOD_COUNT integer,
    PRIMARY KEY (ID),
    FOREIGN KEY (GOOD_ID) REFERENCES goods(ID)
);

INSERT INTO goods (NAME, PRIORITY) VALUES('Шлем Мандалорца', 100);
INSERT INTO goods (NAME, PRIORITY) VALUES('Шлем Люка Скайуокера', 100);
INSERT INTO goods (NAME, PRIORITY) VALUES('Легкий имперский крейсер', 200);
INSERT INTO goods (NAME, PRIORITY) VALUES('Звездолет Бобы Фетта', 80);
INSERT INTO goods (NAME, PRIORITY) VALUES('Микрофайтеры: «Сокол тысячелетия»', 50);

INSERT INTO sales (GOOD_ID, GOOD_COUNT, CREATE_DATE) VALUES(2, 20, '2022-02-15');
INSERT INTO sales (GOOD_ID, GOOD_COUNT, CREATE_DATE) VALUES(5, 45, '2022-01-26');
INSERT INTO sales (GOOD_ID, GOOD_COUNT, CREATE_DATE) VALUES(3, 3, '2022-01-08');
INSERT INTO sales (GOOD_ID, GOOD_COUNT, CREATE_DATE) VALUES(1, 10, '2022-02-02');
INSERT INTO sales (GOOD_ID, GOOD_COUNT, CREATE_DATE) VALUES(1, 10, '2022-01-03');

INSERT INTO warehouse1 (GOOD_ID, GOOD_COUNT) VALUES(5, 100);
INSERT INTO warehouse1 (GOOD_ID, GOOD_COUNT) VALUES(4, 88);

INSERT INTO warehouse2 (GOOD_ID, GOOD_COUNT) VALUES(3, 50);
INSERT INTO warehouse2 (GOOD_ID, GOOD_COUNT) VALUES(1, 2);
