package ua.nure.kardash.Testing.DB.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A bean representation of test, that will be passed to test page
 */
public class Test {
	private int id;
	private List<Question> questions;
	private int questionCount;
	private String name;
	private String description;
	private String address;
	private String subject;
	private int difficulty;
	private int time;

	Test(){};

	public Test(int id, String name, String address, String subject, String description, int difficulty, int time, int questionCount) {
		this.id = id;
		questions = new ArrayList<Question>();
		this.questionCount = questionCount;
		this.name = name;
		this.address = address;
		this.subject = subject;
		this.difficulty = difficulty;
		this.time = time;
		this.description=description;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public String getName() {
		return name;
	}
	public String getSubject() {
		return subject;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public int getTime() {
		return time;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
		this.questionCount = questions.size();
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
