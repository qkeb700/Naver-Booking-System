package kr.or.connect.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

	@GetMapping("myreservation")
	public String myreservation() {
		return "myreservation";
	}
	
	@GetMapping("reserve")
	public String reserve() {
		return "reserve";
	}
	
	@GetMapping("detail")
	public String detail() {
		return "detail";
	}
}
