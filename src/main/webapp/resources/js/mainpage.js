document.addEventListener("DOMContentLoaded", function(){
	
})

function ajaxImage(){
	let oReq = new XMLHttpRequest();
	let promotionItem = document.querySelector("#promotionItem").innerHTML;
	let visualImg = document.querySelector(".visual_img");
	let input = "";
	let promotionsFromJson = "";
	
	oReq.addEventListener("load", function(){
		promotionsFromJson = JSON.parse(oReq.responseText); 
		
		for(let key in promotionsFromJson) {
			input = promotionItem.replace("{}")
		}	
	});
	
	oReq.open("GET", "/reservation/api/promotions");
	oReq.send();
}