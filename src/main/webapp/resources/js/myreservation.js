document.addEventListener("DOMContentLoaded", function(){
	let pageLoadUnit = new PageLoadUnit();
});

function PageLoadUnit() {}
PageLoadUnit.prototype = {
	updateReserve : function() {
		let oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function(){
			let reserveInfo = JSON.parse(oReq.responseText);
		});
		oReq.open("GET", "/reservation/api/reservations?reservationEmail=");
		oReq.send();
	}
}