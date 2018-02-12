package ua.nure.kardash.Testing.DB.Entity;

public class Answer {
	private String text;
	private int id;
	private int questionId;
	private boolean correct;

	public Answer(){};

	public Answer(int id, int questionId, String text, boolean correct) {
		this.text = text;
		this.id = id;
		this.questionId = questionId;
		this.correct = correct;
	}
	public String getText() {
		return text;
	}
	public int getId() {
		return id;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
