CONNECT 'jdbc:derby://localhost:1527/testingDB;create=true;user=admin;password=test';

DROP TABLE test_results;
DROP TABLE answers;
DROP TABLE questions;
DROP TABLE tests;
DROP TABLE users;

----------------------------------------------------------------
-- USERS
----------------------------------------------------------------
CREATE TABLE users(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	login VARCHAR(32) NOT NULL UNIQUE,
	password VARCHAR(32) NOT NULL,
	name VARCHAR(64) NOT NULL,
	role VARCHAR(10) NOT NULL,
	blocked BOOLEAN DEFAULT false,
	settings VARCHAR(32) DEFAULT 'en-light'
);

----------------------------------------------------------------
-- TESTS
----------------------------------------------------------------
CREATE TABLE tests(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	name VARCHAR(64) NOT NULL UNIQUE,
	address VARCHAR(64) NOT NULL UNIQUE,
	description VARCHAR(256) NOT NULL,
	subject VARCHAR(64) NOT NULL,
	difficulty INTEGER NOT NULL,
	time_given INTEGER NOT NULL
);

----------------------------------------------------------------
-- QUESTIONS
----------------------------------------------------------------
CREATE TABLE questions(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	test_id INTEGER NOT NULL REFERENCES tests(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	text VARCHAR(256) NOT NULL
);

----------------------------------------------------------------
-- ANSWERS
----------------------------------------------------------------
CREATE TABLE answers(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	question_id INTEGER NOT NULL REFERENCES questions(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	text VARCHAR(256) NOT NULL,
	correct BOOLEAN DEFAULT false
);

----------------------------------------------------------------
-- TEST_RESULTS
----------------------------------------------------------------
CREATE TABLE test_results(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	test_id INTEGER NOT NULL REFERENCES questions(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	student_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	time_elapsed INTEGER NOT NULL,
	date_taken TIMESTAMP NOT NULL,
	grade INTEGER DEFAULT 0
);


DISCONNECT;