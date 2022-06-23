CREATE TABLE users (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name varchar(80) not null,
	lastname varchar(80) not null,
	nickname varchar(80),
	birth_date date not null,
	email varchar(120) not null,
	created_at timestamp,
	modified_at timestamp,
	version int not null
);
