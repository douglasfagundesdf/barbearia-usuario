CREATE TABLE users (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name varchar(80) not null,
	lastname varchar(80) not null,
	nickname varchar(80),
	age INT(3) not null,
	created_date timestamp
);
