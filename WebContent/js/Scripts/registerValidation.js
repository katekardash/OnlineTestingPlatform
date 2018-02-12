function checkRegistration() {
    var pass1 = document.forms["register"]["pass"].value;
    var pass2 = document.forms["register"]["pass2"].value;
    var name = document.forms["register"]["name"].value;
    var login = document.forms["register"]["login"].value;

    if (pass1 != pass2) {
		displayError("Passwords must match");
        return false;
    }

	if(name.length>64){
		displayError("Name must not be longer than 64 characters");
        return false;
	}

	if(login.length>32){
		displayError("Login must not be longer than 32 characters");
        return false;
	}

	if(pass.length>32){
		displayError("Password must not be longer than 32 characters");
        return false;
	}

	return true;
}

function displayError(message){
	document.getElementById("error-box").style.display = "block";
	document.getElementById("error-message").innerHTML = message;
}