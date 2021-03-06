package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductDisplay;
import kr.or.connect.reservation.dto.ProductItems;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationInfoSetDto;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.ProductDisplayService;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.service.PromotionService;
import kr.or.connect.reservation.service.ReservationService;

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
	
	@Autowired
	ReservationService reservationService;
	
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
	
	@GetMapping("/reservations")
	public ReservationInfoSetDto getReservationInfoSet(@RequestParam(name="reservationEmail", required = true, defaultValue = "") String reservationEmail) {
		return reservationService.getReservationInfoSet(reservationEmail);
	}
	@PostMapping("/reservations")
	public ReservationInfoPriceDto addReservationInfo(@RequestBody ReservationInfoPriceDto reservationInfoPriceDto) {
		return reservationService.addReservation(reservationInfoPriceDto);
	}
	@PutMapping("/reservations/{reservationId}")
	public ReservationInfoPriceDto deleteReservationInfo(@PathVariable int reservationId) {
		return reservationService.changeCancelFlagById(reservationId);
	}
	@PostMapping("/reservations/{reservationInfoId}/comments")
	public String writeComment(@PathVariable(name="reservationInfoId") int reservationInfoId,
			@RequestParam(name = "attachedImage") MultipartFile attachedImage,
		      @RequestParam(name = "comment", required = true) String comment,
		      @RequestParam(name = "productId", required = true) String productId,
		      @RequestParam(name = "score", required = true) int score, HttpServletResponse response,
		      HttpSession session) throws IOException {
		
		String email = (String) session.getAttribute("loginInfo");
		String currentTime = new SimpleDateFormat("yyyyMMddkkmmss").format(new Date());
		String filePath = "";
		
		if(attachedImage.getContentType().startsWith("image")) {
			File folder = new File("c:/tmp_upload");
			if(!folder.exists()) {
				folder.mkdir();
			}
			filePath = (folder.getPath() + "/" + currentTime + "_" + attachedImage.getOriginalFilename()).toLowerCase();
			
			try (FileOutputStream fos = new FileOutputStream(filePath);
					InputStream is = attachedImage.getInputStream(); ){
				
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while((readCount = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		reservationService.writeReview(reservationInfoId, filePath, comment, productId, score);
		response.sendRedirect("../../myreservation?reservationEmail=" + email);
		return null;
	}
}
