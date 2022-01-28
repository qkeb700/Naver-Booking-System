document.addEventListener("DOMContentLoaded", function(){
	
});

function ajaxProductByDisplayId(displayId) {
	let oReq = new XMLHttpRequest();
	
	oReq.open("GET", "/reservation/api/products?displayId=" + displayId);
	oReq.send();
}
