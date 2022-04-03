document.addEventListener("DOMContentLoaded", function(){
	let pageLoadUnit = new PageLoadUnit();
	pageLoadUnit.updateReserve();
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
		console.log(cookieResult);
		let oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function(){
			let reserveInfo = JSON.parse(oReq.responseText);
		});
		oReq.open("GET", "/reservation/api/reservations?reservationEmail=" + cookieResult, true);
		oReq.send();
	}
}

function ReserveIOUnit() {
	
}
ReserveIOUnit.prototype = {
	insertConfirmedReservation : function() {
		
	}
}