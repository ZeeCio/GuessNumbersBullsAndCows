DROP DATABASE IF EXISTS databaseBC;
CREATE DATABASE databaseBC;
USE databaseBC;

CREATE TABLE games (
	gameId INT PRIMARY KEY AUTO_INCREMENT,
    game_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    secretNumbers VARCHAR(25),
    isFinished BOOLEAN
);

CREATE TABLE rounds (
	roundId INT PRIMARY KEY AUTO_INCREMENT,
	gameId INT,
    userNumbers VARCHAR(25) NOT NULL,
    -- exacts INT,
    -- parcials INT,
    EP VARCHAR(255),
    foreign key fk_rounds_gameId (gameId)
		references games(gameId)
);


INSERT INTO games (secretNumbers,isFinished) 
VALUES 
('1425',true),
('1825',false),
('3425',true);

INSERT INTO rounds (userNumbers,gameId,EP) 
VALUES 
('1425',1,'EPEN'),
('1295',1,'NNNN'),
('3425',1,'NNNN');

select * from games;

select * from rounds;

SELECT * FROM games WHERE gameId = 2;
