CREATE DATABASE DB_JDBC
  go
USE DB_JDBC

CREATE TABLE City
(
  City VARCHAR(25) NOT NULL,
  PRIMARY KEY (City)
)

CREATE TABLE  Person
(
  IDPerson INT NOT NULL Identity(1,1),
  Surname VARCHAR(25) NOT NULL,
  Name VARCHAR(25) NOT NULL,
  City VARCHAR(25) NOT NULL,
  Email VARCHAR(45) NULL,
  PRIMARY KEY (IDPerson),
  FOREIGN KEY (City)
  REFERENCES City (City)
)

CREATE TABLE Book
(
  IDBook INT NOT NULL Identity(1,1),
  BookName VARCHAR(45) NOT NULL,
  Author VARCHAR(45) NOT NULL,
  Amount INT NOT NULL,
  PRIMARY KEY (IDBook)
)

CREATE TABLE  PersonBook (
  IDPerson INT NOT NULL,
  IDBook INT NOT NULL,
  PRIMARY KEY (IDPerson, IDBook),
  FOREIGN KEY (IDPerson)
  REFERENCES  Person (IDPerson),
  FOREIGN KEY (IDBook)
  REFERENCES  Book (IDBook)
)

  INSERT INTO book VALUES
('Bible','St. mans',5),
('Kobzar','Shevchenko ',4),
('Harry Potter','J. K. Rowling',1),
('Zakhar Berkut','I. Franko',2),
('The Jungle Book','Rudyard Kipling',1);

INSERT INTO city VALUES ('Herson'),('Kyiv'),('Lviv'),('Poltava'),('Ternopil');

INSERT INTO person VALUES
  ('Koldovskyy','Vyacheslav','Lviv','koldovsky@gmail.com'),
  ('Pavelchak','Andrii','Poltava','apavelchak@gmail.com'),
  ('Soluk','Andrian','Herson','andriansoluk@gmail.com'),
  ('Dubyniak','Bohdan','Ternopil','bohdan.dub@gmail.com'),
  ('Faryna','Igor','Kyiv','farynaihor@gmail.com'),
  ('Kurylo','Volodymyr','Poltava','kurylo.volodymyr@gmail.com'),
  ('Matskiv','Marian','Herson','marian3912788@gmail.com'),
  ('Shyika','Tamara','Kyiv','tamara.shyika@gmail.com'),
  ('Tkachyk','Volodymyr','Ternopil','vova1234.tkachik@gmail.com');

INSERT INTO personbook VALUES (4,1),(5,1),(8,1),(2,2),(6,2),(7,2),(1,3),(1,4),(9,4),(3,5);

/*
CREATE PROCEDURE InsertPersonBook
(
IN SurmanePersonIn varchar(25),
IN BookNameIN varchar(45)
)
BEGIN
	DECLARE msg varchar(40);

  -- checks for present Surname
  IF NOT EXISTS( SELECT * FROM Person WHERE Surname=SurmanePersonIn)
  THEN SET msg = 'This Surname is absent';

  -- checks for present Book
	ELSEIF NOT EXISTS( SELECT * FROM Book WHERE BookName=BookNameIN)
		THEN SET msg = 'This Book is absent';

  -- checks if there are this combination already
	ELSEIF EXISTS( SELECT * FROM personbook
		WHERE IDPerson = (SELECT IDPerson FROM Person WHERE Surname=SurmanePersonIn)
        AND IDBook = (SELECT IDBook FROM Book WHERE BookName=BookNameIN)
        )
        THEN SET msg = 'This Person already has this book';

  -- checks whether there is still such a book
	ELSEIF (SELECT Amount FROM Book WHERE BookName=BookNameIN )
    <= (SELECT COUNT(*) FROM personbook WHERE IDBook=(SELECT IDBook FROM Book WHERE BookName=BookNameIN) )
    THEN SET msg = 'There are no this Book already';

    -- makes insert
    ELSE
		INSERT personbook (IDPerson, IDBook)
        Value ( (SELECT IDPerson FROM Person WHERE Surname=SurmanePersonIn),
			     (SELECT IDBook FROM Book WHERE BookName=BookNameIN) );
		SET msg = 'OK';

	END IF;

	SELECT msg AS msg;

END
*/
