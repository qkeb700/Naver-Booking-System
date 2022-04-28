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
		
		// 글자수 입력 및 제한
		document.querySelector("textarea").addEventListener("keyup", function(){
			let reviewTextLength = document.querySelector(".guide_review").firstElementChild.innerText;
			if(reviewTextLength > 400) {
				alert("400자 제한입니다.");
				document.querySelector("textarea").value = document.querySelector("textarea").value.substring(0, 400);
			}
			document.querySelector("guide_review").firstElementChild.innerText = document.querySelector("textarea").value.length;
		});
	}
}