document.addEventListener("DOMContentLoaded", function(){
	let ajaxPage = new AjaxPage();
	ajaxPage.getApiProduct();
});

function AjaxPage() {
	
}
AjaxPage.prototype.getApiProduct = function() {
	let oReq = new XMLHttpReqeust();
	let currentUrl = window.location.href;
	const url = new URL(currentUrl);
	const urlParams = url.searchParams;
	let displayId = urlParams.get('id');
	let displayJson;
	
	oReq.addEventListener("load", function(){
		displayJson = JSON.parse(oReq.responseText);
	})
	
	oReq.open("GET", "/reservation/api/products" + displayId, true);
	oReq.send();
}

function TicketSelect() {
	
}
TicketSelect.prototype = {
	ticketQtyChange : function(){
		let reserveTemplate = document.querySelector("#reserveTemplate");
		let bindTemplate = Handlebars.compile(reserveTemplate);
		let ticketChange = document.querySelector(".ticket_body");	
	}
	
}