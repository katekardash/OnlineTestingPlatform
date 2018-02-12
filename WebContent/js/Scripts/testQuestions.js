$(document).ready(function(){
	//Selects all "Confirm" buttons, which confirm the given answers and move onto the next question
	$("[id^=qButton]").on('click', answerButtonClick);

	//Navbar buttons, allowing navigation between questions
	$("[id^=navb]").on('click', navButtonClick);

	//Next and Back buttons, which allow quick and easy navigation without submitting results
	$("#pButton").on('click', nextButtonClick);
	$("#bButton").on('click', backButtonClick);

	//Shows the first question
	$("#qBox1").show();

	//Click functions, in all their glory
	function answerButtonClick(e){
		var questionNum = e.currentTarget.id.substring(7);
		var nextQuestion = parseInt(questionNum) + 1;

		if(nextQuestion>maxQuestion){
				nextQuestion=1;
		}

		currentQuestion=nextQuestion;

		var answers = $('input[name="'+questionNum+'"]:checked').map(function () {
			return this.value;
		}).get();

		//Ajax post of answers given to the server, where it is processed
		$.post(window.location.pathname,
		{
			'command': 'submitanswer',
			'question': questionNum,
			'answers': String(answers)
		});

		$('#qBox'+questionNum).hide();
		$('#qBox'+nextQuestion).show();

		$('#navb'+questionNum).addClass('done');

	}


	function nextButtonClick(e){
		var nextQuestion = currentQuestion + 1;

		if(nextQuestion>maxQuestion){
			nextQuestion=1;
		}

		$('#qBox'+currentQuestion).hide();
		$('#qBox'+nextQuestion).show();

		currentQuestion=nextQuestion;
	}

	function backButtonClick(e){
		var prevQuestion = currentQuestion + 1;

		if(prevQuestion<1){
			prevQuestion=1;
		}

		$('#qBox'+currentQuestion).hide();
		$('#qBox'+prevQuestion).show();

		currentQuestion=prevQuestion;

	}

	function navButtonClick(e){
		var selectedNum = e.currentTarget.id.substring(4);

		$('#qBox'+currentQuestion).hide();
		$('#qBox'+selectedNum).show();

		currentQuestion=selectedNum;
	}

	//Interval function which counts down the time left as well as checks if any is left
	setInterval(function(){
		timeLeft--;
		updateTime();

		if(timeLeft<1){
			$("#finishform").submit();
		}

	}, 1000);


	function updateTime(){
		var mins = Math.floor(timeLeft/60);
		var secs = Math.floor(timeLeft%60);
		var timeElapsed = timeTotal-timeLeft;

		if(mins<10){
			var mins="0"+""+mins;
		}

		if(secs<10){
			var secs="0"+""+secs;
		}

		$("#timer").text(mins+":"+secs);
		$("#timetaken").val(timeElapsed);

	}

	//Initial call to set the time
	updateTime();


	});