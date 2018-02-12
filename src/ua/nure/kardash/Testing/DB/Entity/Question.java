package ua.nure.kardash.Testing.DB.Entity;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private List<Answer> answers;
	private String text;
	private int id;
	private int testId;
	private int orderId;
	private boolean answeredCorrectly;

	Question(){};

	public Question(int id, int testId, String text, int orderId) {
		answers = new ArrayList<Answer>();
		this.text = text;
		this.id = id;
		this.testId = testId;
		this.orderId = orderId;
		answeredCorrectly=false;
	}

	public String getText() {
		return text;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public int getId() {
		return id;
	}
	public int getTestId() {
		return testId;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * Parses list of all answers and returns correct ones, separated by a comma. Used for comparison purposes.
	 * @return String containing IDs of correct answers, separated by a comma
	 */
	public String getCorrectAnswers() {
		StringBuilder buff = new StringBuilder();
		for(Answer a:answers){
			if(a.isCorrect()){
				buff.append(a.getId());
				buff.append(",");
			}
		}

		buff.deleteCharAt(buff.length()-1);

		return buff.toString();
	}

	public boolean isAnsweredCorrectly() {
		return answeredCorrectly;
	}

	public void setAnsweredCorrectly(boolean answeredCorrectly) {
		this.answeredCorrectly = answeredCorrectly;
	}

}
