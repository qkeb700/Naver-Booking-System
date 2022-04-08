document.addEventListener("DOMContentLoaded", function(){
	let reservationEmail = "";
	let name = "loginCookie=";
	let cookie = document.cookie.split(';');
	for(let i = 0; i < cookie.length; i++) {
		let chunk = cookie[i];
		if(chunk.indexOf(name)==0) reservationEmail = chunk.substring(name.length, chunk.length);
	}
	let pageLoadUnit = new PageLoadUnit(reservationEmail);
});

function PageLoadUnit(email) {
	this.email = email;
	this.updateReserve(email);
}
PageLoadUnit.prototype = {
	updateReserve : function(email) {
		let oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function(){
			let reserveInfo = JSON.parse(oReq.responseText);
			let getReservation = new GetReservation();

			getReservation.getReservations(reserveInfo);
		});
		oReq.open("GET", "/reservation/api/reservations?reservationEmail=" + email, true);
		oReq.send();
	}
}	
	
	// 예약정보 출력
function GetReservation () {}	
GetReservation.prototype = {
	getReservations : function(reserveInfo) {
		let template = document.querySelector("#reservationArticle").innerHTML;
		let bindTemplate = Handlebars.compile(template);
		reserveInfo.reservations.forEach(function(v){
			let cancelMessage = this.getCancelMessageInfo(v.cancelFlag, v.reservationDate)[0];
			let cancelMessageLocation = this.getCancelMessageInfo(v.cancelFlag, v.reservationDate)[1];
			let data = {
				"reservationInfoId" : v.reservationInfoId,
				productDescription : v.displayInfo.productDescription,
					reservationDate : v.reservationDate.substr(0, v.reservationDate.lastIndexOf(":")),
					productContent : v.displayInfo.productContent,
					placeLot : v.displayInfo.placeLot,
					placeName : v.displayInfo.placeName,
					totalPrice : v.totalPrice,
					productId : v.productId
			}
			let putLocation = document.querySelector(cancelMessageLocation);
			let resultHtml = bindTemplate(data);
			resultHtml = resultHtml.replace(/{cancelMessage}/g, cancelMessage);
			putLocation.innerHTML += resultHtml;
			putLocation.style.display = 'block';
		}.bind(this));
	},
	
	getCancelMessageInfo : function(cancelFlag, reservationDate) {
		if(cancelFlag == 1) {
			return ["", "li.card.used.cancel"];
		}
		let currentTime = new Date();
		let reservationTime = new Date(reservationDate);
		
		if(currentTime < reservationTime) {
			return ["<div class='booking_cancel'> <button class='btn'> <span>취소</span> </button> </div>", "li.card.confirmed"];
		} else {
			return ["<div class='booking_cancel'> <button class='btn'> <span>예매자 리뷰 남기기</span> </button> </div>", "li.card.used"];
		}
	}
	
}	
	
