document.addEventListener("DOMContentLoaded", function(){
	ajaxImage();
	imgSlider();
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
			input = promotionItem.replace("{productImageUrl}", promotionsFromJson[key].productImageUrl)
								 .replace("{description}", promotionsFromJson[key].description)
								 .replace("{placeName}", promotionsFromJson[key].placeName);
			
			visualImg.innerHTML += input;
		}
	});
	
	oReq.open("GET", "/reservation/api/promotions");
	oReq.send();
}

function imgSlider(){
	let visualImg = document.querySelector(".visual_img");
	let totalLength = document.querySelectorAll(".item").length;
	let index = 1;
	
	setInterval(function(){
		if(index > totalLength){
			index = 0;
		}	
		visualImg.style.transform = "translate(-" + 414*index + "px, 0 )";
		visualImg.style.transition = "transform 0.4s ease-in-out";
		index++;
	}, 2000)
}