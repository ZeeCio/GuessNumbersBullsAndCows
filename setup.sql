DROP DATABASE IF EXISTS BC;
CREATE DATABASE BC;

USE BC;

CREATE TABLE game(
id INT PRIMARY KEY AUTO_INCREMENT,
WinningCombination varchar(20) not null,
noRounds int,
isWon boolean
);
