package ua.nure.kardash.Testing.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.jdbc.ClientConnectionPoolDataSource;
import org.apache.log4j.Logger;

import ua.nure.kardash.Testing.DB.Entity.*;

/**
 * This is Database Manager. It manages databases. <br>
 * Retrieves and sets the entity data. Has built-in checks for acceptable values and constraints.
 * <br> Uses pooled datasource to retrieve connections.
 */
public class DBManager {
	private static DBManager instance = null;
	private ClientConnectionPoolDataSource DS;
	private Logger logger;

	public static void main(String[] args) {
		try {
			for(Question q:DBManager.instance().getQuestions(2)){
				System.out.println(q.getText()+"  "+q.getAnswers().size());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Test complete.");

	}

	public synchronized static DBManager instance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/** Makes a new instance as well as initializes connection.
	 * <br> Private because singleton, yo.
	 */
	private DBManager() {
		DS = new ClientConnectionPoolDataSource();
		DS.setServerName("localhost");
		DS.setPortNumber(1527);
		DS.setDatabaseName("testingDB;create=true");
		DS.setUser("admin");
		DS.setPassword("test");

		logger = Logger.getLogger("DB");
	}

	/**
	 * Makes a DB query to get a list of all registered users. Used by admins to
	 * block/unblock users and manage their info
	 *
	 * @return a List of AccountInfo objects representing user info.
	 */
	public List<AccountInfo> getAllUsers() throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		List<AccountInfo> res = new ArrayList<AccountInfo>();

		logger.info("Retrieving all users from database");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT id, login, name, role, blocked, settings FROM Users");
			results = stat.executeQuery();

			while (results.next()) {
				res.add(new AccountInfo(results.getInt(1), results.getString(2), results.getString(3),
						results.getString(4), results.getBoolean(5), results.getString(6)));
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Successfully retrieved "+res.size()+" entries.");

		return res;
	}

	/**
	 * Makes a DB query to get account info based on the login
	 * @return a List of AccountInfo objects representing user info.
	 */
	public AccountInfo getUser(String login) throws SQLException {
		Connection conn = null;
		AccountInfo res = null;
		PreparedStatement stat = null;
		ResultSet results=null;

		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT id, login, name, role, blocked FROM Users WHERE login=?");
			stat.setString(1, login);
			results = stat.executeQuery();

			if (results.next()) {
				res = new AccountInfo(results.getInt(1), results.getString(2), results.getString(3),
						results.getString(4), results.getBoolean(5), results.getString(6));
			} else {
				throw new SQLException("User not found.");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		return res;
	}

	/**
	 * Checks whether login and password given match ones retrieved from the database, or if the login even exists in it
	 * @param login - Given login
	 * @param pass - Given password
	 * @return AccountInfo on successful login; null if password and login don't match, or such login doesnt exist in the database
	 * @throws SQLException
	 */
	public AccountInfo authorizeUser(String login, String pass) throws SQLException {
		Connection conn = null;
		AccountInfo res = null;
		PreparedStatement stat = null;
		ResultSet results=null;

		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT id, login, name, role, blocked, password, settings FROM Users WHERE login=?");
			stat.setString(1, login);
			results = stat.executeQuery();
			if (results.next() && pass.equals(results.getString(6))){
				res = new AccountInfo(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBoolean(5), results.getString(7));
			} else {
				throw new SQLException("User not found or password is incorrect");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		return res;

	}

	/**
	 * Registers a new user with given information, adding it to a database, if possible.
	 * @param login Given user's login
	 * @param pass	Given user's password
	 * @param name	Given user's name
	 * @param role	Given user's role
	 */
	public void registerUser(String login, String pass, String name, String role) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, DEFAULT, DEFAULT)");
			stat.setString(1, login);
			stat.setString(2, pass);
			stat.setString(3, name);
			stat.setString(4, role);

			stat.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResources(conn, stat);
		}

	}

	/**
	 * Makes a DB query to get information of all tests avaliable. Used for display for users (who will take them) and admins (who can edit them).
	 * <br>Can be specified to include
	 * @param includeStubs - whether or not tests with zero questions are included in response. Students' test page omits tests with no questions attached.
	 * @return a List of test objects representing the tests. These objects DO NOT have related question and answer objects, as this is simply a presentation for the list.
	 */
	public List<Test> getAllTests(boolean includeStubs) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		List<Test> res = new ArrayList<Test>();

		logger.info("Retrieving all tests from database");
		try {
			conn = DS.getPooledConnection().getConnection();

			if(includeStubs){
				stat = conn.prepareStatement("SELECT t.id, t.name, t.address, t.subject, t.description, t.difficulty, t.time_given, q.c FROM tests t LEFT JOIN (SELECT q.test_id, COUNT(q.id) c FROM questions q GROUP BY q.test_id) q ON t.id=q.test_id");
			}
			else{
				stat = conn.prepareStatement("SELECT t.id, t.name, t.address, t.subject, t.description, t.difficulty, t.time_given, q.c FROM tests t, (SELECT q.test_id, COUNT(q.id) c FROM questions q GROUP BY q.test_id) q WHERE t.id=q.test_id");
			}

			results = stat.executeQuery();

			while (results.next()) {
				res.add(new Test(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getInt(6), results.getInt(7), results.getInt(8)));
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Successfully retrieved "+res.size()+" entries.");

		return res;
	}

	/**
	 * Retrieves all questions of a given test by ID. Used when editing tests by admin and retrieving questions for TestHandler.
	 * @return A List of Question objects representing user info, along with filled-out answer list.
	 */
	public List<Question> getQuestions(int testid) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		List<Question> res = new ArrayList<Question>();

		logger.info("Retrieving questions from test with id "+testid);
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT q.id, q.test_id, q.text, a.id, a.text, a.correct  FROM questions q LEFT OUTER JOIN answers a ON q.id=a.question_id WHERE q.test_id=? ORDER BY q.id");
			stat.setInt(1, testid);
			results = stat.executeQuery();

			//Minor wizardry ahead. Repeating information is better than a million queries.
			int oldID=0;
			Question q=null;
			int orderID=1;
			while (results.next()) {
				int currID=results.getInt(1);
				if(currID!=oldID){
					oldID=currID;
					q = new Question(currID, results.getInt(2), results.getString(3), orderID);
					orderID++;
					res.add(q);
				}
				if(results.getInt(4)>0){
					q.getAnswers().add(new Answer(results.getInt(4), currID, results.getString(5), results.getBoolean(6)));
				}
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Successfully retrieved "+res.size()+" entries.");

		return res;
	}

	/**
	 * Gets one fully-filled test object. Used by TestHandler to process tests and for test editing.
	 * @return Test object with full list of Questions and Answers.
	 */
	public Test getTest(int testid) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		Test res = null;

		logger.info("Retrieving all tests from database");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT id, t.name, t.address, t.subject, t.description, t.difficulty, t.time_given FROM tests t WHERE t.id=?");
			stat.setInt(1, testid);
			results = stat.executeQuery();
			if (results.next()) {
				res=new Test(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getInt(6), results.getInt(7), 0);
				List<Question> questions = getQuestions(testid);
				res.setQuestions(questions);
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Successfully retrieved test "+results.getString(2));

		return res;
	}

	/**
	 * Gets brief information about test based on its address name. Used to display information on the test page.
	 * @return Test object without the list of questions and answers.
	 */
	public Test getTestInfo(String testAddress) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		Test res=null;

		logger.info("Retrieving test with address "+testAddress+" from the database.");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT t.id, t.name, t.address, t.subject, t.description, t.difficulty, t.time_given, q.c FROM tests t, (SELECT q.test_id, COUNT(q.id) c FROM questions q GROUP BY q.test_id) q WHERE t.address=?");
			stat.setString(1, testAddress);
			results = stat.executeQuery();
			if (results.next()) {
				res=new Test(results.getInt(1), results.getString(2), testAddress, results.getString(4), results.getString(5), results.getInt(6), results.getInt(7), results.getInt(8));
			}
			else{
				throw new SQLException("Test not found by given address");
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Retrieval successfull.");

		return res;
	}


	/**
	 * Makes a DB query to get a list of all tests taken by a given user, along with the additional info.
	 * @return a List of AccountInfo objects representing user info.
	 */
	public List<TestResult> getTestResults(int studentid) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet results=null;
		List<TestResult> res = new ArrayList<TestResult>();

		logger.info("Retrieving test results from database");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("SELECT tr.id, t.name, tr.student_id, tr.time_elapsed, tr.date_taken, tr.grade, a.av FROM test_results tr, tests t, (SELECT AVG(grade) av, test_id FROM test_results GROUP BY test_id) a WHERE tr.test_id=t.id AND a.test_id = t.id AND tr.student_id=?");
			stat.setInt(1, studentid);
			results = stat.executeQuery();

			while (results.next()){
				res.add(new TestResult(results.getInt(1), results.getString(2), studentid, results.getInt(4), results.getTimestamp(5), results.getInt(6), results.getInt(7)));
			}

		} catch (SQLException e) {
			logger.info("Retrieval failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat, results);
		}

		logger.info("Successfully retrieved "+res.size()+" entries.");

		return res;
	}


	/**
	 * Changes user settings of a given user, updating the settings string.
	 * @param userID - ID of user
	 * @param settings - user settings. Formatted as language-theme (ex: en-light)
	 */
	public void updateUserSettings(int userID, String settings) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Retrieving test results from database");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("UPDATE Users SET settings=? WHERE ID=?");
			stat.setString(1, settings);
			stat.setInt(2, userID);
			stat.executeUpdate();

		} catch (SQLException e) {
			logger.info("Update failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully retrieved updated user settings for user "+userID);
	}


	/**
	 * Sets ban or removes ban for a given user
	 * @param userID - ID of user
	 * @param banned - whether a given user will be banned or unbanned
	 */
	public void updateUserBan(int userID, boolean banned) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		System.out.println(banned);

		logger.info("Retrieving test results from database");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("UPDATE Users SET blocked=? WHERE ID=?");
			stat.setBoolean(1, banned);
			stat.setInt(2, userID);
			stat.executeUpdate();

		} catch (SQLException e) {
			logger.info("Update failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully retrieved updated user settings for user "+userID);
	}

	/**
	 * Updates test information, such as name, address, difficulty and time given.
	 * <br> Used by admins to edit tests
	 * @param testName - Name of created test
	 * @param description - Description of the test. Has a larger capacity, but should be kept brief
	 * @param subject - Subject of test. Should be two words max
	 * @param difficulty - Test difficulty, on scale from 1 to 5
	 * @param time - Time given, in minutes. 0 min, 240 max.
	 */
	public void updateTestInfo(int testID, String testName, String description, String subject, int difficulty, int time) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		String address=testName.replace(" ", "").toLowerCase();

		logger.info("Attempting to update test info");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("UPDATE TESTS SET name=?, address=?, description=?, subject=?, difficulty=?, time_given=? WHERE ID=?");
			stat.setString(1, testName);
			stat.setString(2, address);
			stat.setString(3, description);
			stat.setString(4, subject);
			stat.setInt(5, difficulty);
			stat.setInt(6, time);
			stat.setInt(7, testID);

			stat.executeUpdate();
		} catch (SQLException e) {
			logger.info("Update failed: "+e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully updated test info for test "+testID);
	}

	/**
	 * Updates text question. Used to fix errors or improved clarity
	 * <br> For completly changing the question and its answers please instead delete the old one and create a new one.
	 * @param questionID - Question id
	 * @param text - Question text
	 * @throws SQLException
	 */
	public void updateQuestionText(int questionID, String text) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to create a question");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("UPDATE questions SET text=? WHERE id=?");
			stat.setString(1, text);
			stat.setInt(2, questionID);
			stat.execute();

		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully changed text in question with ID "+questionID);
	}

	/**
	 * Updates answer with a given id, changing its text and\or correctness.
	 * @param answerID - ID of answer
	 * @param text - New answer text
	 * @param correct - Whether or not answer is correct
	 */
	public void updateAnswer(int answerID, String text, boolean correct) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to update answer");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("UPDATE answers SET text=?, correct=? WHERE id=?");
			stat.setString(1, text);
			stat.setBoolean(2, correct);
			stat.setInt(3, answerID);
			stat.execute();

		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully changed text in answer with ID "+answerID);
	}

	/**
	 * Adds the general test info (without the questions\answers lists).
	 * <br>Used by admins to create new tests.
	 * @param testName - Name of created test
	 * @param description - Description of the test. Has a larger capacity, but should be kept brief
	 * @param subject - Subject of test. Should be two words max
	 * @param difficulty - Test difficulty, on scale from 1 to 5
	 * @param time - Time given, in minutes. 0 min, 240 max.
	 */
	public void addTestStub(String testName, String description, String subject, int difficulty, int time) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		String address=testName.replace(" ", "").toLowerCase();

		logger.info("Attempting to create test");
		try {
			//INSERT INTO tests VALUES(DEFAULT, 'Java Basics', 'javabasics', 'Basd.', 'Programming', 1, 20);
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("INSERT INTO tests VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)");
			stat.setString(1, testName);
			stat.setString(2, address);
			stat.setString(3, description);
			stat.setString(4, subject);
			stat.setInt(5, difficulty);
			stat.setInt(6, time);
			stat.execute();

		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully created test "+testName);
	}

	/**
	 * Adds a given question to the test, along with the list of questions.
	 * <br> Used by admins to add questions
	 * @param testID - ID of test that the question belongs to
	 * @param text - Question text
	 * @throws SQLException
	 */
	public void addQuestion(int testID, String text) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to create a question");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("INSERT INTO questions VALUES(DEFAULT, ?, ?)");
			stat.setInt(1, testID);
			stat.setString(2, text);
			stat.execute();

		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully created question "+text+" for text ID "+testID);
	}

	/**
	 * Adds the set answer to a question with given ID.
	 * @param questionID
	 * @param answer
	 * @throws SQLException
	 */
	public void addAnswer(int questionID, String text, boolean correct) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to insert answer");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("INSERT INTO answers VALUES(DEFAULT, ?, ?, ?)");
			stat.setInt(1, questionID);
			stat.setString(2, text);
			stat.setBoolean(3, correct);
			stat.execute();


		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully added answer to question with ID "+questionID);
	}

	/**
	 * Inserts journal entry for the completed test.
	 * @param testID - ID of completed test
	 * @param userID - ID of student completing it
	 * @param time_taken - Time taken by the student to complete the test
	 * @param time - Time at which the test was taken
	 * @param grade - Resulting test grade
	 */
	public void addJournalEntry(int testID, int userID, int time_taken, Timestamp time, int grade) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to insert a journal entry");
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("INSERT INTO test_results VALUES(DEFAULT, ?, ?, ?, ?, ?)");

			stat.setInt(1, testID);
			stat.setInt(2, userID);
			stat.setInt(3, time_taken);
			stat.setTimestamp(4, time);
			stat.setInt(5, grade);

			stat.execute();


		} catch (SQLException e) {
			logger.info("Insertion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Successfully inserted journal entry for testID "+testID+" and userID "+userID);
	}


	/**
	 * Deletes the test with a given ID. This also deleted every asociated question and answer, use with caution.
	 * @param testID - ID of test to be deleted
	 */
	public void removeTest(int testID) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to remove test with ID "+testID);
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("DELETE FROM tests WHERE id=?");
			stat.setInt(1, testID);

			stat.execute();


		} catch (SQLException e) {
			logger.info("Deletion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Deletion successfull.");
	}

	/**
	 * Deletes the question with a given ID. This also deleted every asociated answer.
	 * @param questionID - ID of question to be deleted
	 */
	public void removeQuestion(int questionID) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to remove question with ID "+questionID);
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("DELETE FROM questions WHERE id=?");
			stat.setInt(1, questionID);

			stat.execute();

		} catch (SQLException e) {
			logger.info("Deletion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Deletion successfull.");
	}

	/**
	 * Deletes the answer with a given ID.
	 * @param answerID - ID of answer to be deleted
	 */
	public void removeAnswer(int answerID) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		logger.info("Attempting to remove answer with ID "+answerID);
		try {
			conn = DS.getPooledConnection().getConnection();
			stat = conn.prepareStatement("DELETE FROM answers WHERE id=?");
			stat.setInt(1, answerID);

			stat.execute();

		} catch (SQLException e) {
			logger.info("Deletion failed: "+e.getMessage());
			throw e;
		} finally {
			closeResources(conn, stat);
		}

		logger.info("Deletion successfull.");
	}


	/**
	 * Closes a given Connection, PreparedStatement and\or result set. <br>
	 * A convenience\cleanliness method to remove the ugly-looking nesting try-catch-finally loops. <br>
	 * @param conn Database connection.
	 * @param stat SQL statement.
	 * @param res SQL PreparedStatement result.
	 */
	private void closeResources(Connection conn, PreparedStatement stat, ResultSet res) {
		try {
			if(res!=null){
				res.close();
			}
			if(stat!=null){
				stat.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Closes a given Connection, PreparedStatement and\or result set. <br>
	 * A convenience\cleanliness method to remove the ugly-looking nesting try-catch-finally loops. <br>
	 * @param conn Database connection.
	 * @param stat SQL statement.
	 */
	private void closeResources(Connection conn, PreparedStatement stat) {
		try {
			if(stat!=null){
				stat.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
