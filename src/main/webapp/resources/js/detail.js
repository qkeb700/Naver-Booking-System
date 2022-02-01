
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
		// 슬라이더 리스트
		let sliderList = document.querySelectorAll(".detail_swipe .item");
		let showCurrentNum = document.querySelector(".figure_pagination .num");
		// 현재 슬라이더 지표
		let currentIndex  = 1;
		// 총 가지고 있는 슬라이더 개수
		let totalSlider = sliderList.length;
		let showTotalNum = document.querySelector(".figure_pagination .off span");
		let prevBtn = document.querySelector(".btn_prev");
		let nextBtn = document.querySelector(".btn_nxt");
		
		showCurrentNum.innerHTML = currentIndex;
		showTotalNum.innerHTML = totalSlider; 
		
		if(currentIndex == 1) {
			prevBtn.style.display = 'none';
		}
		if(totalSlider == 1) {
			nextBtn.style.display = 'none';
		}
		// 이전 버튼을 누르면 슬라이드가 전으로 가고 가장 왼쪽이면 이전 버튼 안보이기.
		prevBtn.addEventListener("click", function(){
			nextBtn.style.display = 'block';
			currentIndex--;
			showCurrentNum.innerHTML = currentIndex;
			if(currentIndex == 1) {
				prevBtn.style.display = 'none';
			}	
			document.querySelector(".detail_swipe").style.transform = "translate(0)"
		});
		// 버튼 클릭시 다음 슬라이드로 이동, 마지막 슬라이드면 다음 버튼 안보이기.
		nextBtn.addEventListener('click', function(){
			prevBtn.style.display = 'block';
			currentIndex++;
			showCurrentNum.innerHTML = currentIndex;
			if(currentIndex == totalSlider) {
				nextBtn.style.display = 'none';
			}
			document.querySelector(".detail_swipe").style.transform = "translate(-414px)" 
		});
		
		let showDesc = document.querySelector(".store_details .dsc");
		showDesc.innerHTML = displayJson.displayInfo.productContent;
		// 누르면 내용 펼쳐보기 
		$(".bk_more._open").on("click", function(){
			$(".store_details").removeClass("close3");
			$('.bk_more._open').css('display', 'none');
			$('.bk_more._close').css('display', 'block');
		})		
		// 누르면 내용 간략하게 보기
		$(".bk_more._close").on("click", function(){
			$(".store_details").addClass("close3");
			$('.bk_more._open').css('display', 'block');
			$('.bk_more._close').css('display', 'none');
		})
		// 예매하기 누르면 예매 페이지로 링크
		let linkReservation = document.querySelector(".section_btn .bk_btn");
		linkReservation.addEventListener("click", function(){
			location.href = "/reservation/reserve";
		})
		// 예매자 한줄평 평점, 별점 적용
		let totalAvg = document.querySelector(".grade_area .text_value span");
		totalAvg.innerHTML = displayJson.averageScore.toFixed(1);
		starRate = (displayJson.averageScore.toFixed(1) / 5.0) * 100;
		let graphValue = document.querySelector(".graph_value");
		graphValue.style.width = starRate + "%";
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
			"comments" : displayJson.comments.slice(0,3)
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
		
		
		// 탭에서 누르면 상세정보와 오시는길 표시
		let tabDetail = document.querySelector("._detail a");
		let tabPath = document.querySelector("._path a");
		// 전시 상품 소개글
		$(".detail_info_lst .in_dsc").html(displayJson.displayInfo.productContent);
		// 오시는길 정보 입력
		$('.store_name').html(displayJson.displayInfo.productDescription);
		$('.store_addr_bold').html(displayJson.displayInfo.placeStreet);
		$('.addr_old_detail').html(displayJson.displayInfo.placeLot);
		$('.addr_detail').html(displayJson.displayInfo.placeName);
		$('.store_tel').html(displayJson.displayInfo.telephone);
		tabDetail.addEventListener("click", function() {
			$('._detail a').addClass('active');
			$('._path a').removeClass('active');
			$('.detail_location').addClass('hide');
			$(".detail_area_wrap").removeClass('hide');
			
		});
		tabPath.addEventListener("click", function() {
			$('._path a').addClass('active');
			$('._detail a').removeClass('active');
			$(".detail_area_wrap").addClass('hide');
			$('.detail_location').removeClass('hide');
		});
		
	});
	
	oReq.open("GET", "/reservation/api/products/" + displayId);
	oReq.send();
}
