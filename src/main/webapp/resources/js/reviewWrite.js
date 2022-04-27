document.addEventListener("DOMContentLoaded", function(){
	let reviewWritePage = new GetReviewWritePage();
});

function GetReviewWritePage(){
	this.stars = document.querySelectorAll(".rating>input.rating_rdo");
	this.registerEvents();
}
GetReviewWritePage.prototype = {
	// 별점 이벤트 등록
	registerEvents : function() {
		this.stars.forEach(function(v){
			v.addEventListener("click", function(evt){
				document.querySelector(".gray_star").style.color = 'black';
				document.querySelector(".gray_star").innerText = evt.target.value;
				document.querySelector(".review_write_info").style.display = 'none';				
			}.bind(this));
		}.bind(this));
		
		
	}
}