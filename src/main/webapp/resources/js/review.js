
document.addEventListener("DOMContentLoaded", function(){
	ajaxProductByDisplayId();
});

function ajaxProductByDisplayId() {
	let oReq = new XMLHttpRequest();
	let currentUrl = window.location.href;
	const url = new URL(currentUrl);
	const urlParams = url.searchParams;
	let displayId = urlParams.get('id');
	let displayJson;
	
	oReq.addEventListener("load", function() {
		displayJson = JSON.parse(oReq.responseText);
		
		// 예매자 한줄평 평점, 별점 적용
		let totalAvg = document.querySelector(".grade_area .text_value span");
		totalAvg.innerHTML = displayJson.averageScore.toFixed(1);
		starRate = (displayJson.averageScore.toFixed(1) / 5.0) * 100;
		let graphValue = document.querySelector(".graph_value");
		graphValue.style.width = starRate;
		// 한줄평 건수
		let totalComments;
		if(displayJson.comments == null) {
			totalComments = 0;			
		}else{
			totalComments = displayJson.comments.length;		
		}
		let showTotalComments = document.querySelector(".join_count .green");
		showTotalComments.innerHTML = totalComments;
		
		// 한줄평 핸들바 이용해서 가져오기	
		let listReviews = document.querySelector(".list_short_review");
		let commentsTemplate = document.querySelector("#comments").innerHTML;
		console.log(comments);
		let bindTemplate2 = Handlebars.compile(commentsTemplate);
		let data2 = {
			"displayInfo" : displayJson.displayInfo,
			"comments" : displayJson.comments
		}
		Handlebars.registerHelper("saveFileName", function(item){
			return item.commentImages.shift().saveFileName;
		});
		Handlebars.registerHelper("email", function(input){
			return input.substring(0,4) + "****";
		})
		Handlebars.registerHelper("toDouble", function(num){
			let doubleTypeNum = num.toFixed(1);
			return doubleTypeNum;
		});
		
		listReviews.innerHTML = bindTemplate2(data2);
		
		
		
		
	});
	
	oReq.open("GET", "/reservation/api/products/" + displayId);
	oReq.send();
}
