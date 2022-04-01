document.addEventListener("DOMContentLoaded", function(){
	let submitUnit = new SubmitUnit();
	submitUnit.submitForm();
});

function ValidationCheck(){
	
}
ValidationCheck.prototype = {
	checkEmail : function() {
		let inputBox = document.querySelector('#resrv_id');
		let inputEmail = inputBox.value;
		let checking = (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(inputEmail);
		
		if(!inputEmail) {
			inputBox.placeHolder = "이메일을 넣어주세요.";
			alert("입력된 이메일이 없습니다.");
			inputBox.focus();
			return false;
		} else {
			if(!checking) {
				inputBox.placeHolder = "이메일 형식이 잘못되었습니다.";
				alert("올바른 이메일 형식을 입력하세요.");
				inputBox.focus();
				return false;
			}
		}
		return true;
	}
}

function SubmitUnit() {
	
}
SubmitUnit.prototype = {
	submitForm : function() {
		let submitBtn = document.querySelector(".login_btn");
		let form = document.querySelector('#form1');
		submitBtn.addEventListener("click", function(evt) {
			evt.preventDefault();
			let validationCheck = new ValidationCheck();
			let validEmail = validationCheck.checkEmail();
			if(validEmail) {
				form.submit();
			}
		});	
	}
}


