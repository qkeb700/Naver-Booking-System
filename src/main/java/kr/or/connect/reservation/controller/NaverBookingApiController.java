package kr.or.connect.reservation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductDisplay;
import kr.or.connect.reservation.dto.ProductItems;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.ProductDisplayService;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api")
public class NaverBookingApiController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDisplayService productDisplayService;
	
	@GetMapping("/categories")
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}
	
	@GetMapping("/promotions")
	public List<Promotion> getPromotions(){
		return promotionService.getPromotions();
	}
	
	@GetMapping("/products")
	public ProductItems getProducts(@RequestParam(name= "categoryId", defaultValue = "0") int categoryId, 
			@RequestParam(name="start", defaultValue = "0") int start){
		List<Product> products;
		int totalCount;
		ProductItems productItems = new ProductItems();
		
		if(categoryId == 0) {
			products = productService.getProducts(start);
			totalCount = productService.getCount();
			productItems.setItems(products);
			productItems.setTotalCount(totalCount);
			
		} else {
			products = productService.getProductsByCategoryId(categoryId, start);
			totalCount = productService.getCountByCategoryId(categoryId);
			productItems.setItems(products);
			productItems.setTotalCount(totalCount);
		}
		
		return productItems;
	}
	
	@GetMapping("/products/{displayInfoId}")
	public ProductDisplay getProductsByDisplayId(@PathVariable int displayInfoId) {
		return productDisplayService.getProductDisplay(displayInfoId);
	}
	
	
	
}
