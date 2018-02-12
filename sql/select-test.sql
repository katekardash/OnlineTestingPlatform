CONNECT 'jdbc:derby://localhost:1527/testingDB;create=true;user=admin;password=test';


SELECT tr.id, t.name, tr.student_id, tr.time_elapsed, tr.date_taken, tr.grade, a.av FROM test_results tr, tests t, (SELECT AVG(grade) av, test_id FROM test_results GROUP BY test_id) a WHERE tr.test_id=t.id AND a.test_id = t.id AND tr.student_id=1;


DISCONNECT;

