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
		let productImageChange = new ProductImageChange();
		let detailInformation = new DetailInformation();
		let ticketSelect = new TicketSelect();
		let agreementCheck = new AgreementCheck();
		let submitSection = new SubmitSection();
		
		
		productImageChange.imageChange(displayJson);
		
		detailInformation.detailInfo(displayJson);
		
		ticketSelect.ticketTypeChange(displayJson);
		ticketSelect.ticketQtyChange();
		
		agreementCheck.clickAgreement();
		agreementCheck.disableAgreement();
		
		submitSection.submitClick(displayJson);
		
	})
	
	oReq.open("GET", "/reservation/api/products/" + displayId, true);
	oReq.send();
}

function ProductImageChange() {
	
}
ProductImageChange.prototype.imageChange = function(displayJson) {
	let imgThumb = document.querySelector(".img_thumb");
	imgThumb.src = "/reservation/resources/" + displayJson.productImages.shift().saveFileName;
}
function DetailInformation() {
	
}
DetailInformation.prototype.detailInfo = function(displayJson) {
	let placeLot = document.querySelector('.store_details #placeLot');
	let openingHours = document.querySelector(".store_details #openingHours");
	let businessHours = document.querySelector(".store_details").childNodes[7];
	openingHours.innerHTML = displayJson.displayInfo.openingHours;
	placeLot.innerHTML = displayJson.displayInfo.placeLot;
	let array = openingHours.innerHTML.split('\n');
	openingHours.innerHTML = array[0];
	businessHours.innerHTML = array[1];	
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
				let rawPrice = evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML;
				let price = parseInt(evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML)*1000;
				
				rawPrice = rawPrice.split(' ');
				let newPrice = rawPrice[0];
				newPrice = newPrice.split(',');
				let sumPrice = newPrice[0] + newPrice[1];
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
					totalPrice.innerHTML =  totalValue - sumPrice;
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
				let rawPrice = evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML;
				let price = parseInt(evt.target.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].innerHTML);

				rawPrice = rawPrice.split(' ');
				let newPrice = rawPrice[0];
				newPrice = newPrice.split(',');
				let sumPrice = newPrice[0] + newPrice[1];
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
				totalPrice.innerHTML = showQtyInput.getAttribute("value") * sumPrice;
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
function CheckValidation() {
	
}
CheckValidation.prototype = {
	validName : function() {
		let inputName = document.querySelector("input#name");
		let inputNameValue = inputName.value;
		let checkIfValid = (/^[가-힣]+$/).test(inputNameValue);
		
		if(!inputNameValue) {
			alert("예매자를 입력해주세요.");
			inputName.focus();
			return false;
		} else if(!checkIfValid) {
			alert("한글만 입력해주세요.");
			inputNameValue = "";
			inputName.focus();
			return false;
		}
		return true;
		
	},
	validTel : function() {
		let inputTel = document.querySelector("#tel");
		let telValue = inputTel.value;
		let checkIfValid = (/^\d{3}-\d{3,4}-\d{4}$/).test(telValue);
		
		if(!telValue) {
			alert("연락처를 입력해주세요.");
			inputTel.focus();
			return false;
		} else if(!checkIfValid) {
			alert("연락처 번호를 다시 확인해주세요.");
			telValue = "";
			inputTel.focus();
			return false;
		}	
		return true;
	},
	validEmail : function() {
		let inputEmail = document.querySelector("#email");
		let emailValue = inputEmail.value;
		let checkIfValid = (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(emailValue);
	
		if(!emailValue) {
			alert("이메일을 입력해주세요.");
			inputEmail.focus();
			return false;
		} else if(!checkIfValid){
			alert("이메일 형식이 잘못됐습니다. 다시 입력해주세요.");
			inputEmail.focus();
			return false;
		}
		return true;
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
function Price(count, productPriceId, reservationInfoId, reservationInfoPriceId) {
	this.count = count;
	this.productPriceId = productPriceId;
	this.reservationInfoId = reservationInfoId;
	this.reservationInfoPriceId = reservationInfoPriceId;
}
function RequestSend(prices, productId, displayInfoId, reservationName, reservationTel, reservationEmail, reservationDate) {
	this.prices = prices;
	this.productId = productId;
	this.displayInfoId = displayInfoId;
	this.reservationName = reservationName;
	this.reservationTel = reservationTel;
	this.reservationEmail = reservationEmail;
	this.reservationDate = reservationDate;
}
function RequestData() {
	
}
RequestData.prototype = {
	createPricesData : function() {
		let ticketQty = document.querySelectorAll(".ticket_body .qty");
		let prices = [];
		ticketQty.forEach(function(item){
			let count = item.childNodes[1].childNodes[3].childNodes[3].value;
			let productPriceId = item.dataset.productpriceid;
			let price = new Price(count, productPriceId);
			prices.push(price);
		});
		return prices;
	},
	createClientData : function(displayJson) {
		let prices = this.createPricesData();
		let productId = displayJson.displayInfo.productId;
		let displayInfoId = displayJson.displayInfo.displayInfoId;
		let reservationName = document.querySelector('#name').value;
		let reservationTel = document.querySelector('#tel').value;
		let reservationEmail = document.querySelector('#email').value;
		let reservationDate = document.querySelector("#reservationDate").innerHTML;
		
		return new RequestSend(prices, productId, displayInfoId, reservationName, reservationTel, reservationEmail, reservationDate);
	}
}
function SubmitSection() {
	
}
SubmitSection.prototype = {
	submitClick : function(displayJson) {
		let submitBtn = document.querySelector('.bk_btn_wrap');
		submitBtn.addEventListener('click', function() {
			let checkValidation = new CheckValidation();
			let validName = checkValidation.validName();
			let validTel = checkValidation.validTel();
			let validEmail = checkValidation.validEmail();
			if(validName && validTel && validEmail) {
				let requestData = new RequestData();
				let clientData = requestData.createClientData(displayJson);
		
				let oReq = new XMLHttpRequest();
				oReq.addEventListener("load", function() {
					alert("예매에 성공하였습니다.");
					location.href = "/reservation/";
				});
				oReq.open("POST", "/reservation/api/reservations");
				oReq.setRequestHeader('content-type', 'application/json');
				oReq.send(JSON.stringify(clientData));
			}
		})
	}
}

