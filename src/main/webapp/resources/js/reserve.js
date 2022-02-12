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
		
		ticketSelect.ticketQtyChange(displayJson);
		
		console.log(displayJson);
	})
	
	oReq.open("GET", "/reservation/api/products/" + displayId, true);
	oReq.send();
}

function TicketSelect() {
	
}
TicketSelect.prototype = {
	ticketQtyChange : function(displayJson){
		let reserveTemplate = document.querySelector("#reserveTemplate").innerHTML;
		let bindTemplate = Handlebars.compile(reserveTemplate);
		let ticketChange = document.querySelector(".ticket_body");
		let data = {
			"productPrices" : displayJson.productPrices 
		}
		console.log(displayJson.productPrices);	
		Handlebars.registerHelper("changeType", function(type) {
			console.log(type);
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
		ticketChange.innerHTML = bindTemplate(data);
	}
	
}