package kr.or.connect.reservation.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

	@GetMapping("/myreservation")
	public String myreservation() {
		return "myreservation";
	}
	
	@GetMapping("/reserve")
	public String reserve(ModelMap model) {
		LocalDateTime randomDate = LocalDateTime.now().plusDays((long)(Math.random()*6));
		String randomDateText = randomDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		model.addAttribute("randomDateText", randomDateText);
		return "reserve";
	}
	
	@GetMapping("/detail")
	public String detail() {
		return "detail";
	}
	
	@GetMapping("/review")
	public String review() {
		return "review";
	}
}
