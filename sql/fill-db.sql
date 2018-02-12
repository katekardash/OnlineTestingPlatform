CONNECT 'jdbc:derby://localhost:1527/testingDB;create=true;user=admin;password=test';

--Users--
INSERT INTO users VALUES(DEFAULT, 'john1', 'hunter77', 'John Doe', 'student', DEFAULT, DEFAULT);
INSERT INTO users VALUES(DEFAULT, 'maryanne', 'kittens', 'Mary Anne Collins', 'student', DEFAULT, DEFAULT);
INSERT INTO users VALUES(DEFAULT, 'bigboss', 'sudologin', 'Admin McBoss', 'admin', DEFAULT, DEFAULT);
INSERT INTO users VALUES(DEFAULT, 'vasya', 'parol', 'Ð’Ð°Ñ�Ð¸Ð»Ð¸Ð¹ Ð˜Ð²Ð°Ð½Ð¾Ð²', 'student', DEFAULT, DEFAULT);

--Tests--
INSERT INTO tests VALUES(DEFAULT, 'Mathematics 101', 'math101', 'A test on basics of high school mathematics and entry calculus, designed for graduating high school students.','Mathematics', 2, 30);
INSERT INTO tests VALUES(DEFAULT, 'Java Basics', 'javabasics', 'Basic Java test. Tests understanding on syntaxis, some entry-level programming knowlege and general theory.', 'Programming', 1, 20);
INSERT INTO tests VALUES(DEFAULT, 'Biology 101', 'biology101', 'Entry-level biology test designed for high-school graduates who plan on continuing education in fields of biology.' ,'Biology', 4, 45);

--Questions--
INSERT INTO questions VALUES(DEFAULT, 1, 'What is 1+2? ');
INSERT INTO questions VALUES(DEFAULT, 1, 'Prove Dirichle''s 2nd theorem.');
INSERT INTO questions VALUES(DEFAULT, 2, 'Why are netbeans called netbeans?');
INSERT INTO questions VALUES(DEFAULT, 2, 'What is the correct form of main method?');
INSERT INTO questions VALUES(DEFAULT, 3, 'Are hyenas cats or dogs?');
INSERT INTO questions VALUES(DEFAULT, 3, 'Why are cats soft?');

--Answers--
INSERT INTO answers VALUES(DEFAULT, 1, '4', false);
INSERT INTO answers VALUES(DEFAULT, 1, '3', true);
INSERT INTO answers VALUES(DEFAULT, 2, 'How about I do not.', true);
INSERT INTO answers VALUES(DEFAULT, 2, 'I like bunnies', false);
INSERT INTO answers VALUES(DEFAULT, 3, 'And why are Eclipse versions called like THAT?', false);
INSERT INTO answers VALUES(DEFAULT, 3, 'Because coffee', true);
INSERT INTO answers VALUES(DEFAULT, 4, 'Main', false);
INSERT INTO answers VALUES(DEFAULT, 4, 'static main', true);
INSERT INTO answers VALUES(DEFAULT, 5, 'Cat', true);
INSERT INTO answers VALUES(DEFAULT, 5, 'Dog', false);
INSERT INTO answers VALUES(DEFAULT, 6, 'Because we like to hug them', true);
INSERT INTO answers VALUES(DEFAULT, 6, 'Fur is warm and fluffy', true);

--Test results--
INSERT INTO test_results VALUES(DEFAULT, 1,1,615,'2017-09-01 23:03:01', 86);
INSERT INTO test_results VALUES(DEFAULT, 1,2,715,'2017-09-01 21:12:01', 12);
INSERT INTO test_results VALUES(DEFAULT, 1,3,415,'2017-09-01 20:03:01', 56);
INSERT INTO test_results VALUES(DEFAULT, 2,1,615,'2017-09-01 11:30:01', 96);
INSERT INTO test_results VALUES(DEFAULT, 2,3,815,'2017-09-01 12:21:01', 76);


DISCONNECT;