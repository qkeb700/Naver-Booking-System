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
			getReservation.getCountItems();
			getReservation.hideSection();
			getReservation.registerEvents();
			getReservation.registerPopupEvents();
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
	},
	
	getCountItems : function() {
		// 전체 개수
		document.querySelector("ul.summary_board > li:nth-child(1) > a > span").innerText = document.querySelectorAll("article").length;
		// 예약확정
		document.querySelector("ul.summary_board > li:nth-child(2) > a > span").innerText = document.querySelectorAll("div.wrap_mylist > ul > li.card.confirmed > article").length;
		// 이용완료
		document.querySelector("ul.summary_board > li:nth-child(3) > a > span").innerText = document.querySelectorAll("div.wrap_mylist > ul > li:nth-child(2) > article").length;
		// 취소된 예약
		document.querySelector("ul.summary_board > li:nth-child(4) > a > span").innerText = document.querySelectorAll("div.wrap_mylist > ul > li.card.used.cancel > article").length;
	},
	
	hideSection : function() {
		// 이용 예정
		if (document.querySelector("#container > div.ct > div > div.my_summary > ul > li:nth-child(2) > a > span").innerText == 0) document.querySelector("#container > div.ct > div > div.wrap_mylist > ul > li.card.confirmed").style.display = "none";
		// 이용완료
		if (document.querySelector("#container > div.ct > div > div.my_summary > ul > li:nth-child(3) > a > span").innerText == 0) document.querySelector("#container > div.ct > div > div.wrap_mylist > ul > li:nth-child(2)").style.display = "none";
		// 취소된 예약
		if (document.querySelector("#container > div.ct > div > div.my_summary > ul > li:nth-child(4) > a > span").innerText == 0) document.querySelector("#container > div.ct > div > div.wrap_mylist > ul > li.card.used.cancel").style.display = "none";
	},
	
	registerEvents : function() {
		// 취소버튼 이벤트 등록
		document.querySelectorAll("li.card.confirmed div.booking_cancel>button.btn").forEach(function(obj) {
			obj.addEventListener("click", function() {
				let popupLayer = document.querySelector("div.popup_booking_wrapper");
				let title = obj.parentElement.parentElement.querySelector("h4").innerText;
				let reserveDate = obj.parentElement.parentElement.querySelector("ul>li:nth-child(1)>em").innerText;
				let reservationInfoId=obj.parentElement.parentElement.querySelector("em.booking_number").innerText.substr(3);
				
				popupLayer.querySelector("h1>input").value = reservationInfoId;
				popupLayer.querySelector("h1>span").innerText = title;
				popupLayer.querySelector("h1>small").innerText = "예약일: " + reserveDate;
				popupLayer.style.display = "block";
			});
		});		
		// 리뷰남기기 버튼 이벤트 등록
		document.querySelectorAll(".wrap_mylist>ul.list_cards>li:nth-child(2) div.booking_cancel>button.btn").forEach(function(obj){
			obj.addEventListener("click", function(){
				let reservationInfoId = obj.closest("div.card_detail").firstElementChild.innerHTML.substring(3);
				let productId = obj.closest("div.card_body").firstElementChild.value;
				location.href='reviewWrite?reservationInfoId=' + reservationInfoId + '&productId=' + productId;
			});
		})
	},
	
	registerPopupEvents : function() {
		let popupLayer = document.querySelector(".popup_booking_wrapper");
		// close 버튼
		document.querySelector(".popup_btn_close").addEventListener("click", ()=>{popupLayer.style.display='none';});
		// 아니오 버튼
		document.querySelector(".btn_gray>.btn_bottom").addEventListener("click", ()=>{popupLayer.style.display='none'});
		// 예 버튼
		document.querySelector(".btn_green>.btn_bottom").addEventListener("click", ()=>{this.cancelReservation(document.querySelector("#popupId").value)});
	},
	
	// 예약 취소하기
	cancelReservation : function(reservationInfoId) {
		let oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function(){
			let pageLoadUnit = new PageLoadUnit();
			//let getReservation = new GetReservation();
			this.initScreen();
			pageLoadUnit.updateReserve(this.reservationEmail);
			document.querySelector(".popup_booking_wrapper").style.display = 'none';
		}.bind(this));
		oReq.open("put", "/reservation/api/reservations/" + reservationInfoId);
		oReq.send();
	},
	
	// 예약 취소 후, 화면 초기화
	initScreen : function() {
		let template = document.querySelector("#cardDescription").innerHTML;
		let bindTemplate = Handlebars.compile(template);
		
		let details = {
				confirmed : ['#container > div.ct > div > div.wrap_mylist > ul > li.card.confirmed', 'ico_check2', '예약 확정'],
				used : ['#container > div.ct > div > div.wrap_mylist > ul > li:nth-child(2)', 'ico_check2', '이용 완료'],
				canceled : ['#container > div.ct > div > div.wrap_mylist > ul > li.card.used.cancel', 'ico_cancel', '취소된 예약']
		}
		
		for(let key in details) {
			document.querySelector(details[key][0]).innerHTML = '';
			let data = {
				icon : details[key][1],
				description : details[key][2]
			}
			let resultHTML = bindTemplate(data);
			document.querySelector(details[key][0]).innerHTML = resultHTML;
		}
	}
	
	
}	
	
