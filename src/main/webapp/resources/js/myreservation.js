document.addEventListener("DOMContentLoaded", function(){
	let pageLoadUnit = new PageLoadUnit();
	pageLoadUnit.updateReserve();
	//pageLoadUnit.getReservation(reserveInfo);
});

function PageLoadUnit() {}
PageLoadUnit.prototype = {
	updateReserve : function() {
		let name = "loginCookie=";
		let cookie = document.cookie.split(';');
		let cookieResult = "";
		for(let i = 0; i < cookie.length; i++) {
			let chunk = cookie[i];
			if(chunk.indexOf(name)==0) cookieResult = chunk.substring(name.length, chunk.length);
		}
		let oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function(){
			let reserveInfo = JSON.parse(oReq.responseText);
			console.log(reserveInfo);
			this.getReservation(reserveInfo);
		});
		oReq.open("GET", "/reservation/api/reservations?reservationEmail=" + cookieResult, true);
		console.log(cookieResult);
		oReq.send();
	},
	
	// 예약정보 출력
	getReservation : function(reserveInfo) {
		let template = document.querySelector("#reservationArticle").innerHTML;
		let bindTemplate = Handlebars.compile(template);
	}
}
