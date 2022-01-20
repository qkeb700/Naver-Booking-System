document.addEventListener("DOMContentLoaded", function(){
	ajaxImage();
	imgSlider();
	clickCategory();
	ajaxProducts(start);
	moreBtn();
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
let start = 0;
function ajaxProducts(start) {
	let oReq = new XMLHttpRequest();
	let productsFromJson;
	let itemList = document.querySelector("#itemList").innerHTML;
	let input;
	let lstEventBox = document.querySelectorAll(".lst_event_box");
	let totalCount = document.querySelector(".event_lst_txt .pink");
	let categoryId = document.querySelector(".event_tab_lst .item .anchor.active").parentNode.getAttribute("data-category");
	
	oReq.addEventListener("load", function(){
		productsFromJson = JSON.parse(oReq.responseText);
		totalCount.innerHTML = productsFromJson.totalCount + '개';
		
		for(let key in productsFromJson.items) {
			input = itemList.replace("{id}", productsFromJson.items[key].displayInfoId)
							.replace("{description}", productsFromJson.items[key].productDescription)
							.replace("{productImgUrl2}", productsFromJson.items[key].productImageUrl)
							.replace("{description}", productsFromJson.items[key].productDescription)
							.replace("{placeName}", productsFromJson.items[key].placeName)
							.replace("{content}", productsFromJson.items[key].productContent);
		
			if(key % 2 == 0) {
				lstEventBox[0].innerHTML += input;				
			} else {
				lstEventBox[1].innerHTML += input;
			}
		}
	});
	
	oReq.open("GET", "/reservation/api/products?categoryId=" + categoryId + "&start=" + start);
	oReq.send();
}


function clickCategory(){
	let categoryList = document.querySelector(".event_tab_lst");
	categoryList.addEventListener("click", function(evt){
		let targetElem = document.querySelector(".anchor.active");
		if(targetElem){
			document.querySelector(".anchor.active").classList.remove("active");		
		}
		if(evt.target.tagName == "SPAN") {
			evt.target.parentNode.classList.add("active");
			deleteProducts();
			start = 0;
			ajaxProducts(start);
		} else if(evt.target.tagName == "A") {
			evt.target.classList.add("active");
			deleteProducts();
			start = 0;
			ajaxProducts(start);
		}
	});
}

function deleteProducts(){
	let lstEventBox = document.querySelectorAll(".lst_event_box");
	for(let i = 0; i < lstEventBox.length; i++) {
		while(lstEventBox[i].hasChildNodes()){
			lstEventBox[i].removeChild(lstEventBox[i].firstChild);
		}
	}
}
function moreBtn() {
	let moreBtn = document.querySelector(".more .btn");
	
	moreBtn.addEventListener("click", function(){
		start += 4;
		ajaxProducts(start);
	});
}