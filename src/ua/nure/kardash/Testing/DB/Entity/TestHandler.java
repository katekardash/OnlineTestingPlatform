package ua.nure.kardash.Testing.DB.Entity;

/**
 * A class that holds the current test, given answers and calculates percentage answered correctly
 */
public class TestHandler {
	private Test test;
	private int correctAnswers;

	public TestHandler(Test test){
		this.test=test;
	}

	/**
	 * This method accepts questionID and answers arranged in a string and then compares them to equally formatted answers from the question.
	 * <br>If the answers are correct, then question is set as correctly answered. If not, it's set as false.
	 * It is possible to "override" a correct answer with incorrect one.
	 * Unanswered questions are considered answered incorrectly.
	 * @param testID - Order ID of the question
	 * @param answers - String of answer IDs separated by commas
	 */
	public void processAnswers(String questionID, String answers) {
		int qID = Integer.parseInt(questionID);
		Question question = test.getQuestions().get(qID-1);

		String correctAnswers = question.getCorrectAnswers();

		if(correctAnswers.equals(answers)){
			 question.setAnsweredCorrectly(true);
		}
		else{
			 question.setAnsweredCorrectly(false);
		}
	}

	/**
	 * Calculates percentage of correct answers and returns it.
	 * @return % of questions answered correctly
	 */
	public int getTestGrade(){
		correctAnswers=0;
		for(Question q:test.getQuestions()){
			if(q.isAnsweredCorrectly()){
				correctAnswers++;
			}
		}

		return (correctAnswers*100)/test.getQuestionCount();
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

}
