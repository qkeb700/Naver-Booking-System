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
		
		ticketSelect.ticketTypeChange(displayJson);
		ticketSelect.ticketQtyChange();
		
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
			} else if(evt.target.title === "더하기") {
				let showQtyInput = evt.target.previousSibling.previousSibling;
				let plusBtn = evt.target.parentElement.childNodes[3];
				let minusBtn = evt.target.parentElement.childNodes[1];
				console.log(plusBtn);
				let currentCount = showQtyInput.getAttribute("value");
				let changedQty = parseInt(currentCount) + 1;
				if(changedQty > 0) {
					plusBtn.classList.remove("disabled");
					showQtyInput.classList.remove("disabled");
					minusBtn.classList.remove("disabled");
				}
				showQtyInput.setAttribute("value", changedQty);
			}
		})
	}
	
}