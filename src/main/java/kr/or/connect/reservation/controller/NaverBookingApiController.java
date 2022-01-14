package kr.or.connect.reservation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api")
public class NaverBookingApiController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PromotionService promotionService;
	
	@GetMapping("/categories")
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}
	
	@GetMapping("/promotions")
	public List<Promotion> getPromotions(){
		return promotionService.getPromotions();
	}
}
