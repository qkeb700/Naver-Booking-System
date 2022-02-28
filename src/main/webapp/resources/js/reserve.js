document.addEventListener("DOMContentLoaded", function(){
	let ajaxPage = new AjaxPage();
	ajaxPage.getApiProduct();
});

function AjaxPage() {
	
}
AjaxPage.prototype.getApiProduct = function() {
	let oReq = new XMLHttpRequest();
	let currentUrl = window.location.href;
	const url = new URL(currentUrl);
	const urlParams = url.searchParams;
	let displayId = urlParams.get('id');
	let displayJson;
	
	oReq.addEventListener("load", function(){
		displayJson = JSON.parse(oReq.responseText);
		let ticketSelect = new TicketSelect();
		let agreementCheck = new AgreementCheck();
		
		ticketSelect.ticketTypeChange(displayJson);
		ticketSelect.ticketQtyChange();
		//ticketSelect.countTotalTickets();
		
		agreementCheck.clickAgreement();
		agreementCheck.disableAgreement();
		
	})
	
	oReq.open("GET", "/reservation/api/products/" + displayId, true);
	oReq.send();
}

function TicketSelect() {
	
}
TicketSelect.prototype = {
	ticketTypeChange : function(displayJson){
		let reserveTemplate = document.querySelector("#reserveTemplate").innerHTML;
		let bindTemplate = Handlebars.compile(reserveTemplate);
		let ticketChange = document.querySelector(".ticket_body");
		let data = {
			"productPrices" : displayJson.productPrices 
		}	
		Handlebars.registerHelper("changeType", function(type) {
			let changedType;
			switch(type) {
				case 'A':
					changedType = '성인';
					break;
				case 'Y':
					changedType = '청소년';
					break;
				case 'B':
					changedType = '유아';
					break;
				case 'S':
					changedType = '세트';
					break;
				case 'D':
					changedType = '장애인';
					break;
				case 'C':
					changedType = '지역주민';
					break;
				case 'E':
					changedType = '얼리버드';
					break;
				case 'D':
					changedType = '평일';
					break;
				case 'V':
					changedType = 'VIP';
					break;
				default:
					changedType = type + '석';
			}
			return changedType;
		});
		Handlebars.registerHelper("numberPoint", function(num) {
			const cn1 = num.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
			return cn1;
		});
		ticketChange.innerHTML = bindTemplate(data);
	},
	ticketQtyChange : function() {
		let ticketBody = document.querySelector(".ticket_body");
		ticketBody.addEventListener("click", function(evt) {
			if(evt.target.title === "빼기") {
				let showQtyInput = evt.target.nextSibling.nextSibling;
				let minusBtn = evt.target.parentElement.childNodes[1];
				let currentCount = showQtyInput.getAttribute("value");
				let totalPrice = evt.target.parentElement.nextSibling.nextSibling.nextSibling.nextSibling.childNodes[0];
				let totalValue = totalPrice.innerHTML;
				let price = parseInt(evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML)*1000;
				if(currentCount > 0) {
					evt.target.classList.remove("disabled");
				}
				
				let changedQty = parseInt(currentCount) - 1;
				if(changedQty <= 0) {
					changedQty = 0;
					minusBtn.classList.add("disabled");
					showQtyInput.classList.add("disabled");
				}
				showQtyInput.setAttribute("value", changedQty);
				console.log(currentCount);
				if(showQtyInput.getAttribute("value") > 0) {
					totalPrice.innerHTML =  totalValue - price;
					totalPrice.classList.add("on_color");					
				} else {
					totalPrice.innerHTML =  0;
					totalPrice.classList.add("on_color");
				}
				
				
					
			} else if(evt.target.title === "더하기") {
				// 수량 개수 나타내는 박스
				let showQtyInput = evt.target.previousSibling.previousSibling;
				let plusBtn = evt.target.parentElement.childNodes[3];
				let minusBtn = evt.target.parentElement.childNodes[1];
				let totalPrice = evt.target.parentElement.nextSibling.nextSibling.nextSibling.nextSibling.childNodes[0];
				let price = parseInt(evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML)*1000;
				// 현재 수량의 수
				let currentCount = showQtyInput.getAttribute("value");
				// 더하기 버튼 눌러서 하나 추가된 수
				let changedQty = parseInt(currentCount) + 1;
				if(changedQty > 0) {
					plusBtn.classList.remove("disabled");
					showQtyInput.classList.remove("disabled");
					minusBtn.classList.remove("disabled");
				}
				// 수량 개수 박스에 하나 수량 추가되어진 개수 나타내기
				showQtyInput.setAttribute("value", changedQty);
				totalPrice.innerHTML = showQtyInput.getAttribute("value") * price;
			}
			// 총 티켓 매수 변경하기
			let totalCount = document.querySelector("#totalCount");
			let allTickets = 0;
			let countControlInput = document.querySelectorAll(".count_control_input");
			for(let i = 0; i < countControlInput.length; i++) {
				allTickets += parseInt(countControlInput[i].getAttribute("value"));
			}
			totalCount.innerHTML = allTickets;
		})
	}
	
}
function checkValidation() {
	
}
checkValidation.prototype = {
	validName : function() {
		let inputName = document.querySelector("input#name");
		let inputNameValue = inputName.value;
		let checkIfValid = (/^[가-힣]+$/).test(inputNameValue);
		
		if(!inputNameValue) {
			alert("예매자를 입력해주세요.");
			inputName.focus();
		} else if(!checkIfValid) {
			alert("한글만 입력해주세요.");
			inputNameValue = "";
			inputName.focus();
		}
		
	},
	validTel : function() {
		let inputTel = document.querySelector("#tel");
		let telValue = inputTel.value;
		let checkIfValid = (/^\d{3}-\d{3,4}-\d{4}$/).test(telValue);
		
		if(!telValue) {
			alert("예매자를 입력해주세요.");
			inputTel.focus();
		} else if(!checkIfValid) {
			alert("연락처 번호를 다시 확인해주세요.");
			telValue = "";
			inputTel.focus();
		}	
	},
	validEmail : function() {
		let inputEmail = document.querySelector("#email");
		let emailValue = inputEmail.value;
		let checkIfValid = (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(emailValue);
	
		if(!emailValue) {
			alert("이메일을 입력해주세요.");
			inputEmail.focus();
		} else if(!checkIfValid){
			alert("이메일 형식이 잘못됐습니다. 다시 입력해주세요.");
			inputEmail.focus();
		}
	}
}

function AgreementCheck() {
	
}
AgreementCheck.prototype = {
	clickAgreement : function() {
		let agreementBox = document.querySelector(".section_booking_agreement");
		agreementBox.addEventListener("click", function(evt){
			let agreement;
			if(evt.target.classList.contains('.btn_agreement') || evt.target.classList.contains('btn_text')
						|| evt.target.classList.contains('fn-down2')) {
				agreement = evt.target.closest('.agreement');
				if(agreement.classList.contains('open')) {
					agreement.classList.remove('open');
					agreement.childNodes[3].childNodes[1].innerHTML = '보기';
				} else {
					agreement.classList.add('open');
					agreement.childNodes[3].childNodes[1].innerHTML = '닫기';
				}
			}
		})
	},
	disableAgreement : function() {
		let checkInput = document.querySelector("#chk3");
		let reserveBtn = document.querySelector('.bk_btn_wrap');
		checkInput.addEventListener("click", function() {
			if(checkInput.checked == true) {
				reserveBtn.classList.remove('disable');
			} else {
				reserveBtn.classList.add('disable');
			}
		})
	}
}


