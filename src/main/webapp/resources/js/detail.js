
document.addEventListener("DOMContentLoaded", function(){
	ajaxProductByDisplayId();
});

function ajaxProductByDisplayId() {
	let oReq = new XMLHttpRequest();
	let currentUrl = window.location.href;
	const url = new URL(currentUrl);
	const urlParams = url.searchParams;
	let displayId = urlParams.get('id');
	let detailSwipe = document.querySelector('.detail_swipe');
	let productImagesData = document.querySelector("#productImages").innerHTML;
	let displayJson;
	
	oReq.addEventListener("load", function() {
		displayJson = JSON.parse(oReq.responseText);
		// 핸들바 이용해서 script 내용과 대체되게 만든다.
		let bindTemplate = Handlebars.compile(productImagesData);
		let data = {
			"productImageUrl" : displayJson.productImages.slice(0,2),
			
			"productDescription" : displayJson.displayInfo.productDescription
			
		};
		detailSwipe.innerHTML = bindTemplate(data);
		console.log(detailSwipe);
		console.log(bindTemplate(data));
	});
	
	oReq.open("GET", "/reservation/api/products/" + displayId);
	oReq.send();
}
